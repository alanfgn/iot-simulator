package br.alan.device;

import java.util.ArrayList;
import java.util.List;

public class DeviceSettings {

    private String name;
    private String host;
    private Integer port;
    private String role;

    private StateDeviceEnum state;
    private Integer updateTime;

    public DeviceSettings(String name, String host, Integer port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public DeviceSettings(String name, String host, Integer port, String role) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.role = role;
    }

    public DeviceSettings(String name, Integer port, String role, StateDeviceEnum state, Integer updateTime) {
        this.name = name;
        this.port = port;
        this.role = role;

        this.state = state;
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public StateDeviceEnum getState() {
        return state;
    }

    public void setState(StateDeviceEnum state) {
        this.state = state;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isClientSetting() {
        return this.host != null;
    }

    public boolean isClientMainSetting() {
        return !this.isClientSetting();
    }
}
