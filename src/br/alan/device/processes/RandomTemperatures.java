package br.alan.device.processes;

import br.alan.device.Device;
import br.alan.device.DeviceClient;

import java.io.IOException;
import java.util.Random;

public class RandomTemperatures implements Runnable {

    private Device device;
    private Random random = new Random();

    public RandomTemperatures(Device device) {
        this.device = device;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String temperatura = random.nextInt(40) + " Celsius";

                for(DeviceClient deviceClient : device.getClients()){
                    deviceClient.send(temperatura);
                }

                Thread.sleep((1000 * device.getDeviceSettings().getUpdateTime()));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
