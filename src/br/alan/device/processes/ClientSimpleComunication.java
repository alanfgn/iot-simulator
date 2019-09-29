package br.alan.device.processes;

import br.alan.device.Device;
import br.alan.device.DeviceClient;

import java.io.IOException;
import java.util.Scanner;

public class ClientSimpleComunication implements Runnable {

    private Device device;
    private DeviceClient deviceClient;
    private Scanner sc;

    public ClientSimpleComunication(Device device, DeviceClient deviceClient) {
        this.deviceClient = deviceClient;
        this.device = device;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        try{
            device.print("Estabelecendo conexão simples com cliente ");
            String message = deviceClient.nextMessage();
            device.print(" client say >> "+message);
            device.print(" waiting response ...");
            deviceClient.send(sc.nextLine());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
