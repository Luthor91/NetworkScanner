package com.networkscanner.scanner;

import com.networkscanner.model.SystemInfo;

import java.util.ArrayList;

public class SystemScanner {

    public SystemScanner() {
        // Aucun besoin d'initialiser une dépendance externe comme OSHI
    }

    public SystemInfo scanSystem() {
        SystemInfo info = new SystemInfo();

        // Récupérer les informations sur l'OS depuis System
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");

        // Construire la chaîne d'informations sur l'OS
        info.setOsVersion(String.format("%s %s (%s)", osName, osVersion, osArch));

        // Uptime - Non disponible avec System, définir une valeur par défaut
        info.setUptime(-1); // Ou une valeur informative comme "non disponible"

        // Services actifs - Non disponible avec System
        info.setActiveServices(new ArrayList<>()); // Liste vide ou message "non applicable"

        // Utilisateurs connectés - Non disponible avec System
        info.setConnectedUsers(new ArrayList<>()); // Liste vide ou message "non applicable"

        return info;
    }
}
