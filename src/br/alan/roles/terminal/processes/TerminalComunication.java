package br.alan.roles.terminal.processes;

import br.alan.device.DeviceClient;
import br.alan.device.processes.ClientSimpleComunication;
import br.alan.roles.terminal.TerminalRole;
import br.alan.roles.truck.GarbageOverFlowException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TerminalComunication implements Runnable {

    private TerminalRole terminalRole;
    private List<DeviceClient> clients = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();

    public TerminalComunication(TerminalRole terminalRole) {
        this.terminalRole = terminalRole;
    }

    @Override
    public void run() {
        try {
            while (true) {
//                this.terminalRole.getDevice().print("Verificando novas conexões");
                List<DeviceClient> newClients = this.terminalRole
                        .getDevice()
                        .getClients()
                        .stream()
                        .filter(c -> !clients.contains(c))
                        .collect(Collectors.toList());

                for (DeviceClient deviceClient: newClients){
                    if(!deviceClient.getSocket().isClosed() && deviceClient.getSocket().isConnected()) {

                        terminalRole.getDevice().print("Adding new client to comunication ");

                        Thread thread = new Thread(new ClientSimpleComunication(terminalRole.getDevice(), deviceClient));
                        thread.run();

                        this.clients.add(deviceClient);
                        this.threads.add(thread);
                    }
                }

                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
