package com.networkscanner.scanner;

import com.networkscanner.model.HardwareInfo;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

public class HardwareScanner {
    private final SystemInfo si;
    private final HardwareAbstractionLayer hal;

    public HardwareScanner() {
        si = new SystemInfo();
        hal = si.getHardware();
    }

    public HardwareInfo scanHardware() {
        HardwareInfo info = new HardwareInfo();

        // CPU Info
        CentralProcessor processor = hal.getProcessor();
        info.setProcessorName(processor.getProcessorIdentifier().getName());
        info.setProcessorFrequency(formatFrequency(processor.getMaxFreq()));

        // Memory Info
        GlobalMemory memory = hal.getMemory();
        info.setTotalRam(memory.getTotal());
        info.setUsedRam(memory.getTotal() - memory.getAvailable());

        // Disk Info
        long totalSpace = hal.getDiskStores().stream()
            .mapToLong(disk -> disk.getSize())
            .sum();
        info.setTotalDiskSpace(totalSpace);
        info.setAvailableDiskSpace(totalSpace - hal.getDiskStores().stream()
            .mapToLong(disk -> disk.getWrites())
            .sum());

        // System Info
        info.setManufacturer(si.getOperatingSystem().getManufacturer());
        info.setModel("Unknown"); // Would need platform-specific code to get model

        return info;
    }

    private String formatFrequency(long hz) {
        return String.format("%.2f GHz", hz / 1000000000.0);
    }
}