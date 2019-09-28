package br.alan.device.processes;

import br.alan.device.Device;

public class DeviceProcessesFactory {

    private static final String RANDOM_TEMPERATURE = "rdntemperature";

    public static Runnable getDeviceProcess(String processNanme, Device device){
        switch (processNanme.toLowerCase()){
            case RANDOM_TEMPERATURE:
                return new RandomTemperatures(device);
            default:
                throw new IllegalArgumentException();
        }
    }


}
