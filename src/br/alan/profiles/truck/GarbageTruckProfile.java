package br.alan.profiles.truck;

import br.alan.device.Device;
import br.alan.profiles.Profile;
import br.alan.profiles.truck.processes.GarbageTruckController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GarbageTruckProfile implements Profile {

    private Device device;
    private boolean inMotion;

    private double[] garbagesAmount = new double[4];
    private double[] centralRecyclingGarbagesAmount = new double[4];


    public GarbageTruckProfile(Device device) {
        this.device = device;
    }

    @Override
    public List<Runnable> getProcesses() {

        List<Runnable> processes = new ArrayList<>();
        processes.add(new GarbageTruckController(this));

        return processes;
    }

    public synchronized Device getDevice() {
        return device;
    }

    public synchronized void setDevice(Device device) {
        this.device = device;
    }

    public synchronized boolean isInMotion() {
        return inMotion;
    }

    public synchronized void setInMotion(boolean inMotion) {
        this.inMotion = inMotion;
    }

    public synchronized void setPaper(double amount) {
        this.garbagesAmount[0] = amount;
    }

    public synchronized double getPaper() {
        return this.garbagesAmount[0];
    }

    public synchronized void setMetal(double amount) {
        this.garbagesAmount[1] = amount;
    }

    public synchronized double getMetal() {
        return this.garbagesAmount[1];
    }

    public synchronized void setGlass(double amount) {
        this.garbagesAmount[2] = amount;
    }

    public synchronized double getGlass() {
        return this.garbagesAmount[2];
    }

    public synchronized void setPlactic(double amount) {
        this.garbagesAmount[3] = amount;
    }

    public synchronized double getPlactic() {
        return this.garbagesAmount[3];
    }

    public double[] getGarbagesAmount() {
        return garbagesAmount;
    }

    public void setGarbagesAmount(double[] garbagesAmount) {
        this.garbagesAmount = garbagesAmount;
    }

    public double[] getCentralRecyclingGarbagesAmount() {
        return centralRecyclingGarbagesAmount;
    }

    public void setCentralRecyclingGarbagesAmount(double[] centralRecyclingGarbagesAmount) {
        this.centralRecyclingGarbagesAmount = centralRecyclingGarbagesAmount;
    }

    public void setCentralRecyclingGarbagesAmount(String centralRecyclingGarbagesAmount) {
        double[] amount = new double[4];

        amount[0] = Double.valueOf(centralRecyclingGarbagesAmount.charAt(0));
        amount[1] = Double.valueOf(centralRecyclingGarbagesAmount.charAt(1));
        amount[2] = Double.valueOf(centralRecyclingGarbagesAmount.charAt(2));
        amount[3] = Double.valueOf(centralRecyclingGarbagesAmount.charAt(3));

        this.setCentralRecyclingGarbagesAmount(amount);
    }


}
