package br.alan.commands;

import br.alan.device.Device;

import java.io.IOException;

public class ConnectCommand extends DeviceCommand {


    private String deviceName;

    private String name;
    private String host;
    private Integer port;

    public ConnectCommand(String deviceName, String name, String host, Integer port) {
        this.deviceName = deviceName;
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public ConnectCommand(String deviceName, String name) {
        this.deviceName = deviceName;
        this.name = name;
    }

    @Override
    public void run() {
        Device device = this.getDevice(this.deviceName);
        try{
            if( this.host != null && this.port != null) {
                device.connect(name, host, port);
            } else {
                device.connect(this.name);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
