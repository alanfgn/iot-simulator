package br.alan.device;

import br.alan.device.processes.PrintSocketMessages;
import br.alan.roles.RoleFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Device implements Runnable {

    private ServerSocket serverSocket;
    private DeviceSettings deviceSettings;

    private List<DeviceClient> clients = new ArrayList<>();
    private List<DeviceClient> servers = new ArrayList<>();

    private List<Thread> processes = new Vector<>();

    private List<DeviceSettings> knownDevices = new ArrayList<>();
    private Random rd = new Random();

    public Device(DeviceSettings deviceSettings, List<DeviceSettings> knownDevices) {
        this.deviceSettings = deviceSettings;
        this.knownDevices = knownDevices;
    }

    public Device(DeviceSettings deviceSettings) {
        this.deviceSettings = deviceSettings;
    }

    @Override
    public void run() {
        try {

            this.serverSocket = new ServerSocket(deviceSettings.getPort());
            this.print(" Ouvindo na porta: " + deviceSettings.getPort());

            if (this.deviceSettings.getState().equals(StateDeviceEnum.ATIVADO)
                    && this.deviceSettings.getRole() != null) {

                this.print("Rodando processos do papel: " + this.deviceSettings.getRole());

                for (Runnable process : RoleFactory
                        .getDeviceProcess(this.deviceSettings.getRole(), this).getProcesses()) {

                    this.execute(process);
                    this.print("Rodando " + process.getClass().getName());
                }
            }

            while (true) {
                DeviceClient deviceClient = new DeviceClient(serverSocket.accept(), this);
                this.print("Recebendo conexão");

                this.clients.add(deviceClient);
            }

        } catch (IOException e) {
            e.printStackTrace();
            this.suicide();
        }

    }

    public DeviceClient connect(String name, String host, Integer port) throws IOException {
        this.knownDevices.add(new DeviceSettings(name, host, port));
        return this.connect(name);
    }

    public DeviceClient connect(String name, String host, Integer port, String role) throws IOException {
        this.knownDevices.add(new DeviceSettings(name, host, port, role));
        return this.connect(name);
    }

    public DeviceClient connect(String name) throws IOException {
        DeviceSettings clientDevice = knownDevices
                .stream()
                .filter(x -> x.getName().equals(name)).findFirst().orElse(null);

        DeviceClient deviceClient = new DeviceClient(clientDevice, this);
        this.print("Conectando com: " + clientDevice.getName());

        this.servers.add(deviceClient);

        return deviceClient;
    }

    public Thread execute(Runnable process) {
        Thread thread = new Thread(process);
        thread.start();

        this.print("Executando " + process.getClass().getName());

        this.processes.add(thread);
        return thread;
    }

    private void suicide() {
        this.killAllProcesses();

        this.print(" - Suicide - ");
        Thread.currentThread().interrupt();
    }

    public void killAllProcesses() {
        this.processes.forEach(x -> x.interrupt());
    }

    public List<DeviceClient> getClients() {
        return clients;
    }

    public List<DeviceClient> getServers() {
        return servers;
    }

    public DeviceSettings getDeviceSettings() {
        return deviceSettings;
    }

    public void print(String message) {
        System.out.println(" " + this.deviceSettings.getName() + " > " + message);
    }

    public DeviceSettings getRandomDeviceByRole(String role){
        List<DeviceSettings> devices = this.knownDevices.stream()
                .filter(kd -> kd.getRole()!= null && kd.getRole().equals(role))
                .collect(Collectors.toList());

        return devices.get(rd.nextInt(devices.size()));
    }

}
