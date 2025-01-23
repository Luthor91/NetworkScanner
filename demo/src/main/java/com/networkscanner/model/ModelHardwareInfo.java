package com.networkscanner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModelHardwareInfo {
    @JsonProperty
    private String processorName;
    @JsonProperty
    private String processorFrequency;
    @JsonProperty
    private long totalRam;
    @JsonProperty
    private long usedRam;
    @JsonProperty
    private long totalDiskSpace;
    @JsonProperty
    private long availableDiskSpace;
    @JsonProperty
    private String manufacturer;
    @JsonProperty
    private String model;

    // Getters and Setters
    public String getProcessorName() { return processorName; }
    public void setProcessorName(String processorName) { this.processorName = processorName; }

    public String getProcessorFrequency() { return processorFrequency; }
    public void setProcessorFrequency(String processorFrequency) { this.processorFrequency = processorFrequency; }

    public long getTotalRam() { return totalRam; }
    public void setTotalRam(long totalRam) { this.totalRam = totalRam; }

    public long getUsedRam() { return usedRam; }
    public void setUsedRam(long usedRam) { this.usedRam = usedRam; }

    public long getTotalDiskSpace() { return totalDiskSpace; }
    public void setTotalDiskSpace(long totalDiskSpace) { this.totalDiskSpace = totalDiskSpace; }

    public long getAvailableDiskSpace() { return availableDiskSpace; }
    public void setAvailableDiskSpace(long availableDiskSpace) { this.availableDiskSpace = availableDiskSpace; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}
