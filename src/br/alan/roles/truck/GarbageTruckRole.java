package br.alan.roles.truck;

import br.alan.device.Device;
import br.alan.roles.Role;
import br.alan.roles.truck.processes.GarbageTruckController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GarbageTruckRole implements Role {

    private Device device;
    private boolean inMotion;

    private GarbageAmount garbageAmount = new GarbageAmount();
    private GarbageAmount recyclingCenterGarbageAmount = new GarbageAmount();


    public GarbageTruckRole(Device device) {
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

    public GarbageAmount getGarbageAmount() {
        return garbageAmount;
    }

    public void setGarbageAmount(GarbageAmount garbageAmount) {
        this.garbageAmount = garbageAmount;
    }

    public GarbageAmount getRecyclingCenterGarbageAmount() {
        return recyclingCenterGarbageAmount;
    }

    public void setRecyclingCenterGarbageAmount(GarbageAmount recyclingCenterGarbageAmount) {
        this.recyclingCenterGarbageAmount = recyclingCenterGarbageAmount;
    }

    public static List<GarbageTypeEnum> possibleGarbagesToDischarge(GarbageAmount sender, GarbageAmount receiver) {
        return receiver
                .getAvailableGarbages()
                .stream()
                .filter(g -> sender.getNotEmptyGarbages().contains(g))
                .collect(Collectors.toList());
    }

    public List<GarbageTypeEnum> possibleGarbagesToDischarge() {
        return GarbageTruckRole.possibleGarbagesToDischarge(
                this.getGarbageAmount(), this.getRecyclingCenterGarbageAmount());
    }


}
