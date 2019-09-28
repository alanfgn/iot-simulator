package br.alan.device.processes;

import br.alan.device.DeviceClient;

import java.io.IOException;

public class PrintSocketMessages implements Runnable {

    private DeviceClient client;

    public PrintSocketMessages(DeviceClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            client.receive();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
