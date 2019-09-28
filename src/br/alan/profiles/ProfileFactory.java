package br.alan.profiles;

import br.alan.device.Device;
import br.alan.profiles.truck.GarbageTruckProfile;

public class ProfileFactory {

    public static Profile getDeviceProcess(String profileName, Device device) {
        switch (profileName.toLowerCase()){
            case Profile.GARBAGE_TRUCK:
                return new GarbageTruckProfile(device);
            default:
                throw new IllegalArgumentException();
        }
    }

}
