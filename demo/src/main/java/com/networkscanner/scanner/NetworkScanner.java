package com.networkscanner.scanner;

import com.networkscanner.model.NetworkInfo;
import org.pcap4j.core.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class NetworkScanner {
    private static final int TIMEOUT = 1000; // 1 second timeout

    public NetworkInfo scanTarget(String ipAddress) {
        NetworkInfo info = new NetworkInfo();
        info.setIpAddress(ipAddress);

        try {
            // Get hostname
            InetAddress addr = InetAddress.getByName(ipAddress);
            info.setHostname(addr.getHostName());

            // Get MAC address using ARP
            try {
                PcapNetworkInterface nif = Pcaps.getDevByAddress(addr);
                if (nif != null) {
                    byte[] mac = nif.getLinkLayerAddresses().get(0).getAddress();
                    info.setMacAddress(formatMacAddress(mac));
                }
            } catch (PcapNativeException e) {
                e.printStackTrace();
            }

            // Scan common ports
            List<Integer> openPorts = scanPorts(ipAddress);
            info.setOpenPorts(openPorts);

            // Measure ping
            long ping = measurePing(ipAddress);
            info.setPingLatency(ping);

            // Try to detect OS (simplified)
            info.setOperatingSystem(detectOS(ipAddress));

            // Get connection type
            info.setConnectionType(detectConnectionType());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }

    private List<Integer> scanPorts(String ipAddress) {
        List<Integer> openPorts = new ArrayList<>();
        int[] commonPorts = {80, 443, 22, 21, 3389, 3306, 5432};

        for (int port : commonPorts) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(ipAddress, port), TIMEOUT);
                openPorts.add(port);
            } catch (Exception e) {
                // Port is closed or filtered
            }
        }
        return openPorts;
    }

    private long measurePing(String ipAddress) {
        try {
            InetAddress addr = InetAddress.getByName(ipAddress);
            long startTime = System.currentTimeMillis();
            if (addr.isReachable(TIMEOUT)) {
                return System.currentTimeMillis() - startTime;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String detectOS(String ipAddress) {
        // Simplified OS detection based on TTL and port signature
        try {
            InetAddress addr = InetAddress.getByName(ipAddress);
            if (addr.isReachable(TIMEOUT)) {
                // This is a simplified example. Real OS detection would be more complex
                return "Unknown OS"; // In reality, you'd use nmap or similar tools
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private String detectConnectionType() {
        try {
            NetworkInterface ni = NetworkInterface.getNetworkInterfaces().nextElement();
            if (ni.isVirtual()) return "Virtual";
            if (ni.supportsMulticast()) return "Ethernet";
            return "Unknown";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    private String formatMacAddress(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (byte b : mac) {
            sb.append(String.format("%02X:", b));
        }
        return sb.substring(0, sb.length() - 1);
    }
}