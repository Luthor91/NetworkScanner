package com.networkscanner.utils;

public class OSValidator {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    public static String getOsName() {
        return OS;
    }

    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    public static boolean isAdministrator() {
        if (isWindows()) {
            String groups = System.getenv("USERGROUPS");
            return groups != null && groups.contains("Administrators");
        } else {
            // Pour Unix/Linux/Mac, vérifie si l'utilisateur est root
            String user = System.getProperty("user.name");
            return "root".equals(user);
        }
    }

    public static boolean hasRequiredPermissions() {
        // Vérifie si l'application a les permissions nécessaires pour le scanning réseau
        if (isWindows() || isUnix()) {
            return isAdministrator();
        }
        // Sur Mac, vérifie les permissions spécifiques
        if (isMac()) {
            return true; // Implémenter la vérification spécifique à macOS si nécessaire
        }
        return false;
    }
}