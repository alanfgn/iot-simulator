package br.alan.roles.terminal;

import br.alan.device.Device;
import br.alan.roles.Role;
import br.alan.roles.terminal.processes.TerminalComunication;
import br.alan.roles.truck.processes.GarbageTruckController;

import java.util.ArrayList;
import java.util.List;

public class TerminalRole implements Role {

    private Device device;

    public TerminalRole(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public List<Runnable> getProcesses() {
        List<Runnable> processes = new ArrayList<>();
        processes.add(new TerminalComunication(this));

        return processes;
    }
}
