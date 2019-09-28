package br.alan.profiles.truck.processes;

import br.alan.device.DeviceClient;
import br.alan.profiles.Profile;
import br.alan.profiles.truck.GarbageTruckProfile;

import java.io.IOException;
import java.util.Scanner;

public class GarbageTruckController implements Runnable {

    private GarbageTruckProfile garbageTruckProfile;

    public GarbageTruckController(GarbageTruckProfile garbageTruckProfile) {
        this.garbageTruckProfile = garbageTruckProfile;
    }

    @Override
    public void run() {
        try {

            while (true) {
                DeviceClient deviceClient = this.garbageTruckProfile.getDevice().connect(Profile.RECYCLING_TRUCK);

                Scanner sc = new Scanner(deviceClient.getInputStream());
                String garbadeAmount = sc.nextLine();
                String [] aux = garbadeAmount.split(garbadeAmount, ' ');

                this.garbageTruckProfile.setCentralRecyclingGarbagesAmount(aux[1]);
                

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
