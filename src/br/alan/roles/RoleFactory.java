package br.alan.roles;

import br.alan.device.Device;
import br.alan.roles.terminal.TerminalRole;
import br.alan.roles.truck.GarbageTruckRole;

public class RoleFactory {

    public static Role getDeviceProcess(String roleName, Device device) {
        switch (roleName){
            case Role.GARBAGE_TRUCK:
                return new GarbageTruckRole(device);
            case Role.TERMINAL:
                return new TerminalRole(device);
            default:
                throw new IllegalArgumentException();
        }
    }

}
