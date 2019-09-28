package br.alan.profiles;

import br.alan.device.Device;
import br.alan.profiles.truck.GarbageTruckProfile;

public class ProfileFactory {

    private static final String GARBAGE_TRUCK = "garbage_truck";

    public static Profile getDeviceProcess(String profileName, Device device) {
        switch (profileName.toLowerCase()){
            case GARBAGE_TRUCK:
                return new GarbageTruckProfile(device);
            default:
                throw new IllegalArgumentException();
        }
    }

}
