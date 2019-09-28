package br.alan.device;

import java.util.ArrayList;
import java.util.List;

public class DeviceSettings {

    private String name;
    private Integer port;
    private String host;
    private StateDeviceEnum state;
    private Integer updatePeriod;

    private String profile;
    private List<String> mainProcesses = new ArrayList<>();

    public DeviceSettings(String name, String host, Integer port) {
        this.name = name;
        this.port = port;
        this.host = host;
    }

    public DeviceSettings(String name, Integer port, StateDeviceEnum state, Integer updatePeriod) {
        this.name = name;
        this.port = port;
        this.state = state;
        this.updatePeriod = updatePeriod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public StateDeviceEnum getState() {
        return state;
    }

    public void setState(StateDeviceEnum state) {
        this.state = state;
    }

    public Integer getUpdatePeriod() {
        return updatePeriod;
    }

    public void setUpdatePeriod(Integer updatePeriod) {
        this.updatePeriod = updatePeriod;
    }

    public String getServerApresentation(){
        if(this.state.equals(StateDeviceEnum.DESATIVADO)){
            return state.getName();
        }
        return  this.name + " " + this.state.getName() + " " + this.getUpdatePeriod().toString();
    }

    public String getClientApresentation(String name) {
        return  this.name + " CONECTAR " + name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void addProcess(String runnable) {
        this.mainProcesses.add(runnable);
    }

    public List<String> getMainProcesses() {
        return mainProcesses;
    }
}
