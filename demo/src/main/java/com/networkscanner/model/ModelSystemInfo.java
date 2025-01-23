package com.networkscanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ModelSystemInfo {
    @JsonProperty
    private String osVersion;
    @JsonProperty
    private long uptime;
    @JsonProperty
    private List<String> activeServices;
    @JsonProperty
    private List<String> connectedUsers;

    // Getters and Setters
    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public List<String> getActiveServices() {
        return activeServices;
    }

    public void setActiveServices(List<String> activeServices) {
        this.activeServices = activeServices;
    }

    public List<String> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(List<String> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }
}
