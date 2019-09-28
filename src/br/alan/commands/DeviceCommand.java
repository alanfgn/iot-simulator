package br.alan.commands;

import br.alan.device.Device;

import java.util.ArrayList;
import java.util.List;

abstract class DeviceCommand implements  Runnable {

    public static List<Device> devices = new ArrayList<>();
    public static List<Thread> threads = new ArrayList<>();


    public static Device getDevice(String deviceName) {
        return devices.stream().filter(x -> x.getName().equals(deviceName)).findFirst().orElse(null);
    }
}
