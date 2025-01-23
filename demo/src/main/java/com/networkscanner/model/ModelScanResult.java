package com.networkscanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ModelScanResult {
    @JsonProperty
    private String targetIp;
    @JsonProperty
    private LocalDateTime scanTime;
    @JsonProperty
    private ModelNetworkInfo networkInfo;
    @JsonProperty
    private ModelHardwareInfo hardwareInfo;
    @JsonProperty
    private ModelSystemInfo systemInfo;
    @JsonProperty
    private boolean scanSuccessful;
    @JsonProperty
    private String errorMessage;

    // Getters and Setters
    public String getTargetIp() { return targetIp; }
    public void setTargetIp(String targetIp) { this.targetIp = targetIp; }

    public LocalDateTime getScanTime() { return scanTime; }
    public void setScanTime(LocalDateTime scanTime) { this.scanTime = scanTime; }

    public ModelNetworkInfo getNetworkInfo() { return networkInfo; }
    public void setNetworkInfo(ModelNetworkInfo networkInfo) { this.networkInfo = networkInfo; }

    public ModelHardwareInfo getHardwareInfo() { return hardwareInfo; }
    public void setHardwareInfo(ModelHardwareInfo hardwareInfo) { this.hardwareInfo = hardwareInfo; }

    public ModelSystemInfo getSystemInfo() { return systemInfo; }
    public void setSystemInfo(ModelSystemInfo systemInfo) { this.systemInfo = systemInfo; }

    public boolean isScanSuccessful() { return scanSuccessful; }
    public void setScanSuccessful(boolean scanSuccessful) { this.scanSuccessful = scanSuccessful; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}