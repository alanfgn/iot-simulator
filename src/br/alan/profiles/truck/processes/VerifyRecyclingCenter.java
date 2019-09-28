package br.alan.profiles.truck.processes;

import br.alan.device.Device;
import br.alan.profiles.truck.GarbageTruckProfile;

import java.io.IOException;

public class VerifyRecyclingCenter implements Runnable {

    private Device device;
    private GarbageTruckProfile garbageTruckProfile;

    public VerifyRecyclingCenter(Device device, GarbageTruckProfile garbageTruckProfile) {
        this.device = device;
        this.garbageTruckProfile = garbageTruckProfile;
    }

    @Override
    public void run() {
        try {
            device.connect("CR");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
