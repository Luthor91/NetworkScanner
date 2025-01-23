// model/NetworkInfo.java
package com.networkscanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class NetworkInfo {
    @JsonProperty
    private String ipAddress;
    @JsonProperty
    private String macAddress;
    @JsonProperty
    private String hostname;
    @JsonProperty
    private List<Integer> openPorts;
    @JsonProperty
    private String operatingSystem;
    @JsonProperty
    private long pingLatency;
    @JsonProperty
    private String connectionType;
    @JsonProperty
    private String bandwidthSpeed;

    // Getters and Setters
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    
    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    
    public List<Integer> getOpenPorts() { return openPorts; }
    public void setOpenPorts(List<Integer> openPorts) { this.openPorts = openPorts; }
    
    public String getOperatingSystem() { return operatingSystem; }
    public void setOperatingSystem(String operatingSystem) { this.operatingSystem = operatingSystem; }
    
    public long getPingLatency() { return pingLatency; }
    public void setPingLatency(long pingLatency) { this.pingLatency = pingLatency; }
    
    public String getConnectionType() { return connectionType; }
    public void setConnectionType(String connectionType) { this.connectionType = connectionType; }
    
    public String getBandwidthSpeed() { return bandwidthSpeed; }
    public void setBandwidthSpeed(String bandwidthSpeed) { this.bandwidthSpeed = bandwidthSpeed; }
}