package com.networkscanner.scanner;

import com.networkscanner.model.ModelSystemInfo;
import oshi.SystemInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SystemScanner {
    private final SystemInfo systemInfo;
    private final oshi.software.os.OperatingSystem os;

    public SystemScanner() {
        this.systemInfo = new SystemInfo();
        // Utiliser la bonne méthode pour accéder au système d'exploitation
        this.os = systemInfo.getOperatingSystem();  // Changé de getOperatingSystem() à getOs()
    }

    public ModelSystemInfo scanSystem() {
        ModelSystemInfo info = new ModelSystemInfo();

        // OS Version
        info.setOsVersion(String.format("%s %s",
            os.getFamily(),
            os.getVersionInfo().toString()));

        // Uptime
        info.setUptime(os.getSystemUptime());

        // Active Services
        List<String> services = os.getServices().stream()
            .filter(service -> service.getState().equals("RUNNING"))
            .map(service -> service.getName())
            .collect(Collectors.toList());
        info.setActiveServices(services);

        // Connected Users
        List<String> users = new ArrayList<>();
        os.getSessions().forEach(session -> 
            users.add(session.getUserName()));
        info.setConnectedUsers(users);

        return info;
    }
}