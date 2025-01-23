package com.networkscanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class ScanResult {
    @JsonProperty
    private String targetIp;
    @JsonProperty
    private LocalDateTime scanTime;
    @JsonProperty
    private NetworkInfo networkInfo;
    @JsonProperty
    private HardwareInfo hardwareInfo;
    @JsonProperty
    private SystemInfo systemInfo;
    @JsonProperty
    private boolean scanSuccessful;
    @JsonProperty
    private String errorMessage;

    // Getters and Setters
    public String getTargetIp() { return targetIp; }
    public void setTargetIp(String targetIp) { this.targetIp = targetIp; }

    public LocalDateTime getScanTime() { return scanTime; }
    public void setScanTime(LocalDateTime scanTime) { this.scanTime = scanTime; }

    public NetworkInfo getNetworkInfo() { return networkInfo; }
    public void setNetworkInfo(NetworkInfo networkInfo) { this.networkInfo = networkInfo; }

    public HardwareInfo getHardwareInfo() { return hardwareInfo; }
    public void setHardwareInfo(HardwareInfo hardwareInfo) { this.hardwareInfo = hardwareInfo; }

    public SystemInfo getSystemInfo() { return systemInfo; }
    public void setSystemInfo(SystemInfo systemInfo) { this.systemInfo = systemInfo; }

    public boolean isScanSuccessful() { return scanSuccessful; }
    public void setScanSuccessful(boolean scanSuccessful) { this.scanSuccessful = scanSuccessful; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}