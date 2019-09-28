package br.alan.commands;

import br.alan.device.Device;
import br.alan.device.DeviceSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RunCommand extends DeviceCommand {

    private DeviceSettings deviceSettings;
    private List<DeviceSettings> knownDevices = new ArrayList<>();

    public RunCommand(DeviceSettings deviceSettings, List<DeviceSettings> knownDevices) {
        this.deviceSettings = deviceSettings;
        this.knownDevices = knownDevices;
    }

    public RunCommand(DeviceSettings deviceSettings) {
        this.deviceSettings = deviceSettings;
    }

    @Override
    public void run() {
        Device device = new Device(this.deviceSettings, this.knownDevices);
        Thread thread = new Thread(device);
        thread.start();

        this.devices.add(device);
        this.threads.add(thread);
    }
}
