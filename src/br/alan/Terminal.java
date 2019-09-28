package br.alan;

import br.alan.commands.ConnectCommand;
import br.alan.commands.RunCommand;
import br.alan.device.DeviceSettings;
import br.alan.device.StateDeviceEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Terminal {

    private static final String STATUS = "status";
    private static final String RUN = "run";
    private static final String CONNECT = "conectar";

    public static Runnable interpret(String command){

        String header = command.split(" ")[0];

        switch (header) {
            case RUN:
                return getRunCommand(command);
            case CONNECT:
                return getConnectCommand(command);
            default:
                throw new IllegalArgumentException();

        }
    }

    public static RunCommand getRunCommand(String command){
        try{

            String[] parameters = command.split(" ");
            List<DeviceSettings> devices = new ArrayList<>();

            List<String> conf = readFile(parameters[1]);
            String[] confParametes = conf.get(0).split(" ");
            DeviceSettings deviceSettings = new DeviceSettings(
                    confParametes[0],
                    Integer.valueOf(confParametes[3]),
                    StateDeviceEnum.valueOfName(confParametes[1]),
                    Integer.valueOf(confParametes[2]));

            if (confParametes.length > 4) {
                deviceSettings.setProfile(confParametes[4]);
            }

            if(parameters.length > 2){
                for(String confDevice : readFile(parameters[2])) {
                    String[] deviceParameters = confDevice.split(" ");
                    DeviceSettings otherDeviceSetting = new DeviceSettings(
                            deviceParameters[0],
                            deviceParameters[1],
                            Integer.valueOf(deviceParameters[2]));

                    devices.add(otherDeviceSetting);
                }
            }

            return new RunCommand(deviceSettings, devices);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ConnectCommand getConnectCommand(String command){
        String[] parameters = command.split(" ");
        if(parameters.length == 5) {
            return new ConnectCommand(
                    parameters[1],
                    parameters[2],
                    parameters[3],
                    Integer.valueOf(parameters[4])
            );
        } else {
            return new ConnectCommand(parameters[1], parameters[2]);
        }
    }

    public static List<String> readFile(String filneName) throws  IOException{
        File file = new File(filneName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        List<String> lines = new ArrayList<>();

        String st;
        while ((st = br.readLine()) != null) {
            lines.add(st);
        }

        return lines;
    }

}
