package br.alan.profiles.truck;

import br.alan.device.Device;
import br.alan.profiles.Profile;

import java.util.List;

public class GarbageTruckProfile implements Profile {

    private Device device;

    public GarbageTruckProfile(Device device) {
        this.device = device;
    }

    @Override
    public List<Runnable> getProcesses() {
        return null;
    }
}
