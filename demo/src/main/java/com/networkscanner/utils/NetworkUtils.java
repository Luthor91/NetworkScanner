package com.networkscanner.utils;

import java.net.*;
import java.util.*;

public class NetworkUtils {
    public static List<String> getLocalIPRanges() {
        List<String> ipRanges = new ArrayList<>();
        
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                
                // Ignorer les interfaces non actives et loopback
                if (!networkInterface.isUp() || networkInterface.isLoopback()) {
                    continue;
                }

                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress address = interfaceAddress.getAddress();
                    
                    // Ignorer les adresses IPv6 pour simplicité
                    if (address instanceof Inet4Address) {
                        String ip = address.getHostAddress();
                        short prefixLength = interfaceAddress.getNetworkPrefixLength();
                        
                        // Ajouter la plage d'IP basée sur le masque réseau
                        ipRanges.add(calculateIPRange(ip, prefixLength));
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        
        return ipRanges;
    }

    private static String calculateIPRange(String ip, short prefixLength) {
        try {
            // Convertir l'IP en bytes
            String[] parts = ip.split("\\.");
            int[] ipBytes = new int[4];
            for (int i = 0; i < 4; i++) {
                ipBytes[i] = Integer.parseInt(parts[i]);
            }

            // Calculer le masque réseau
            int mask = -1 << (32 - prefixLength);
            
            // Calculer l'adresse réseau
            int network = ((ipBytes[0] << 24) | (ipBytes[1] << 16) | 
                          (ipBytes[2] << 8) | ipBytes[3]) & mask;
            
            // Calculer l'adresse de broadcast
            int broadcast = network | (~mask);
            
            // Formater l'adresse réseau
            String networkAddr = String.format("%d.%d.%d.%d",
                (network >> 24) & 0xFF,
                (network >> 16) & 0xFF,
                (network >> 8) & 0xFF,
                network & 0xFF);
            
            // Formater l'adresse de broadcast
            String broadcastAddr = String.format("%d.%d.%d.%d",
                (broadcast >> 24) & 0xFF,
                (broadcast >> 16) & 0xFF,
                (broadcast >> 8) & 0xFF,
                broadcast & 0xFF);
            
            return networkAddr + " - " + broadcastAddr;
        } catch (Exception e) {
            e.printStackTrace();
            return ip + "/" + prefixLength;
        }
    }

    public static List<String> generateIPRange(String startIP, String endIP) {
        List<String> ipList = new ArrayList<>();
        try {
            long start = ipToLong(startIP);
            long end = ipToLong(endIP);
            
            for (long i = start; i <= end; i++) {
                ipList.add(longToIP(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipList;
    }

    private static long ipToLong(String ipAddress) {
        String[] ipAddressInArray = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);
            result += ip * Math.pow(256, power);
        }
        return result;
    }

    private static String longToIP(long ip) {
        return ((ip >> 24) & 0xFF) + "." +
               ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + "." +
               (ip & 0xFF);
    }

    public static boolean isValidIPAddress(String ipAddress) {
        try {
            if (ipAddress == null || ipAddress.isEmpty()) {
                return false;
            }
            
            String[] parts = ipAddress.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String part : parts) {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            }
            
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}