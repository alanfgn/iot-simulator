package br.alan.device;

import br.alan.device.processes.DeviceProcessesFactory;
import br.alan.device.processes.PrintSocketMessages;
import br.alan.device.processes.RandomTemperatures;
import br.alan.profiles.Profile;
import br.alan.profiles.ProfileFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Device implements Runnable {

    private ServerSocket serverSocket;
    private DeviceSettings deviceSettings;

    private List<DeviceClient> clients = new ArrayList<>();
    private List<DeviceClient> servers = new ArrayList<>();

    private List<Thread> processes = new Vector<>();

    private List<DeviceSettings> knownDevices = new ArrayList<>();

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
            this.print(" Server ouvindo na porta: " + deviceSettings.getPort());

            if (this.deviceSettings.getState().equals(StateDeviceEnum.ATIVADO)) {

                for (String processName : this.deviceSettings.getMainProcesses()) {
                    Runnable process = DeviceProcessesFactory.getDeviceProcess(processName, this);
                    this.execute(process);
                }

                if (this.deviceSettings.getProfile() != null) {
                    for (Runnable process : ProfileFactory
                            .getDeviceProcess(this.deviceSettings.getProfile(), this)
                            .getProcesses()) {
                        this.execute(process);
                    }
                }

            }

            while (true) {
                DeviceClient deviceClient = new DeviceClient(serverSocket.accept(), this);
                this.print("Recebendo conexão");

                this.execute(new PrintSocketMessages(deviceClient));
                deviceClient.send(deviceSettings.getServerApresentation());

                this.clients.add(deviceClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.suicide();
        }

    }

    public void connect(String name, String host, Integer port) throws IOException {
        DeviceSettings deviceSettings = new DeviceSettings(name, host, port);
        this.knownDevices.add(deviceSettings);
        this.connect(name);
    }

    public DeviceClient connect(String name) throws IOException {
        DeviceSettings clientDevice = knownDevices.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
        DeviceClient deviceClient = new DeviceClient(clientDevice, this);

        this.execute(new PrintSocketMessages(deviceClient));
        deviceClient.send(this.deviceSettings.getClientApresentation(name));
        this.servers.add(deviceClient);
        return deviceClient;
    }

    public void execute(Runnable process) {
        Thread thread = new Thread(process);
        thread.start();

        this.processes.add(thread);
    }

    private void suicide() {
        this.killAllProcesses();
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

    public String getName() {
        return this.deviceSettings.getName();
    }

    public Integer getUpdateTime() {
        return this.deviceSettings.getUpdatePeriod();
    }

    public DeviceSettings getDeviceSettings() {
        return deviceSettings;
    }

    public void setDeviceSettings(DeviceSettings deviceSettings) {
        this.deviceSettings = deviceSettings;
    }

    protected void print(String message) {
        System.out.println(" " + getName() + " > " + message);
    }

}
