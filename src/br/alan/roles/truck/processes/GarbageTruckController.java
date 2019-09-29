package br.alan.roles.truck.processes;

import br.alan.device.Device;
import br.alan.device.DeviceClient;
import br.alan.device.DeviceSettings;
import br.alan.roles.Role;
import br.alan.roles.truck.GarbageAmount;
import br.alan.roles.truck.GarbageOverFlowException;
import br.alan.roles.truck.GarbageTruckRole;
import br.alan.roles.truck.GarbageTypeEnum;

import java.io.IOException;
import java.util.Scanner;

public class GarbageTruckController implements Runnable {

    private GarbageTruckRole garbageTruckProfile;

    public GarbageTruckController(GarbageTruckRole garbageTruckProfile) {
        this.garbageTruckProfile = garbageTruckProfile;
    }

    @Override
    public void run() { // all this code is about a pattern definied in class, so nothing here is important
        try {

            Device device = this.garbageTruckProfile.getDevice();

            DeviceSettings deviceSettings;
            DeviceClient deviceClient;

            GarbageAmount garbageAmount;
            String garbageAmountString;

            String[] response;

            while (true) {

                device.print("Verificando Central de reciclagem");

                deviceSettings = device.getRandomDeviceByRole(Role.RECYCLING_TRUCK);
                deviceClient = device.connect(deviceSettings.getName());

                deviceClient.send(device.getDeviceSettings().getName()+" STATUS "+deviceClient.getName());


                garbageAmountString = deviceClient.nextMessage();
                response = garbageAmountString.split(" ");

                this.garbageTruckProfile.getRecyclingCenterGarbageAmount().setGarbagesAmount(response[1], 1);

                if (!this.garbageTruckProfile.getRecyclingCenterGarbageAmount().isFull() && this.garbageTruckProfile.getGarbageAmount().isAny()) {
                    device.print("Indo pra central de reciclagem");

                    Thread.sleep(10000);

                    garbageAmount = new GarbageAmount();
                    for (GarbageTypeEnum garbageTypeEnum : this.garbageTruckProfile.possibleGarbagesToDischarge()) {
                        garbageAmount.sumGarbageAmount(garbageTypeEnum, 1);

                        this.garbageTruckProfile.getGarbageAmount().sumGarbageAmount(garbageTypeEnum, -1);
                        this.garbageTruckProfile.getRecyclingCenterGarbageAmount().sumGarbageAmount(garbageTypeEnum, 1);
                    }

                    deviceClient.send(device.getDeviceSettings().getName()+" DESCARREGAR "+deviceClient.getName());
                    deviceClient.send(device.getDeviceSettings().getName()+" "+garbageAmount.toString()+" "+deviceClient.getName());

                }

                deviceClient.die();

                if (!this.garbageTruckProfile.getGarbageAmount().isFull()) {
                    deviceSettings = device.getRandomDeviceByRole(Role.CONTAINER);
                    deviceClient = device.connect(deviceSettings.getName());

                    device.print("Indo pra um container");

                    deviceClient.send(device.getDeviceSettings().getName()+" STATUS "+deviceClient.getName());

                    garbageAmountString = deviceClient.nextMessage();
                    response = garbageAmountString.split(" ");
                    garbageAmount = new GarbageAmount();
                    garbageAmount.setGarbagesAmount(response[1], 1);

                    if (!this.garbageTruckProfile.getGarbageAmount().isFull() && garbageAmount.isAny()) {
                        GarbageAmount garbageAmountAux = new GarbageAmount();

                        for (GarbageTypeEnum garbageTypeEnum : GarbageTruckRole.possibleGarbagesToDischarge(garbageAmount, this.garbageTruckProfile.getGarbageAmount())){
                            garbageAmountAux.sumGarbageAmount(garbageTypeEnum, 1);
                            this.garbageTruckProfile.getGarbageAmount().sumGarbageAmount(garbageTypeEnum, 1);
                        }

                        deviceClient.send(device.getDeviceSettings().getName()+" DESCARREGAR "+deviceClient.getName());
                        deviceClient.send(device.getDeviceSettings().getName()+" "+garbageAmountAux.toString()+" "+deviceClient.getName());
                    }


                    deviceClient.die();
                }
            }

        } catch (IOException | InterruptedException | GarbageOverFlowException e) {
            e.printStackTrace();
        }
    }

}
