package com.networkscanner.gui;

import com.networkscanner.model.ScanResult;
import com.networkscanner.scanner.NetworkScanner;
import com.networkscanner.utils.JsonExporter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame {
    private IPRangePanel ipRangePanel;
    private ScanResultPanel resultPanel;
    private JButton scanButton;
    private JButton exportButton;
    private JProgressBar progressBar;
    private List<ScanResult> currentResults;

    public MainWindow() {
        setTitle("Network Scanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        initComponents();
        currentResults = new ArrayList<>();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Main layout
        setLayout(new BorderLayout(5, 5));
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create components
        ipRangePanel = new IPRangePanel();
        resultPanel = new ScanResultPanel();
        createButtonPanel();
        
        // Add components
        mainPanel.add(ipRangePanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        mainPanel.add(createStatusPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Initialize menus
        createMenuBar();
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        scanButton = new JButton("Scan");
        scanButton.addActionListener(e -> startScan());
        
        exportButton = new JButton("Export Results");
        exportButton.addActionListener(e -> exportResults());
        exportButton.setEnabled(false);
        
        buttonPanel.add(scanButton);
        buttonPanel.add(exportButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout(5, 5));
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        statusPanel.add(progressBar, BorderLayout.CENTER);
        return statusPanel;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Export Results");
        exportItem.addActionListener(e -> exportResults());
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        // Tools Menu
        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem refreshItem = new JMenuItem("Refresh IP Ranges");
        refreshItem.addActionListener(e -> ipRangePanel.refreshIPRanges());
        toolsMenu.add(refreshItem);
        
        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }

    private void startScan() {
        List<String> selectedIPs = ipRangePanel.getSelectedIPs();
        if (selectedIPs.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please select at least one IP range to scan.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Disable UI during scan
        scanButton.setEnabled(false);
        ipRangePanel.setEnabled(false);
        progressBar.setValue(0);
        currentResults.clear();

        // Start scanning in background
        SwingWorker<Void, ScanResult> worker = new SwingWorker<Void, ScanResult>() {
            @Override
            protected Void doInBackground() throws Exception {
                int total = selectedIPs.size();
                NetworkScanner scanner = new NetworkScanner();
                
                for (int i = 0; i < selectedIPs.size(); i++) {
                    String ip = selectedIPs.get(i);
                    try {
                        ScanResult result = new ScanResult();
                        result.setTargetIp(ip);
                        result.setNetworkInfo(scanner.scanTarget(ip));
                        result.setScanSuccessful(true);
                        publish(result);
                    } catch (Exception e) {
                        ScanResult result = new ScanResult();
                        result.setTargetIp(ip);
                        result.setScanSuccessful(false);
                        result.setErrorMessage(e.getMessage());
                        publish(result);
                    }
                    setProgress((i + 1) * 100 / total);
                }
                return null;
            }

            @Override
            protected void process(List<ScanResult> chunks) {
                for (ScanResult result : chunks) {
                    currentResults.add(result);
                    resultPanel.addResult(result);
                }
            }

            @Override
            protected void done() {
                // Re-enable UI
                scanButton.setEnabled(true);
                ipRangePanel.setEnabled(true);
                exportButton.setEnabled(!currentResults.isEmpty());
                progressBar.setValue(100);
                
                JOptionPane.showMessageDialog(MainWindow.this,
                    "Scan completed: " + currentResults.size() + " hosts scanned.",
                    "Scan Complete", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((Integer) evt.getNewValue());
            }
        });

        worker.execute();
    }

    private void exportResults() {
        if (currentResults.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No results to export.", "Export Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            JsonExporter.exportToJson(currentResults);
            JOptionPane.showMessageDialog(this,
                "Results exported successfully to Downloads folder.",
                "Export Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error exporting results: " + e.getMessage(),
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Network Scanner\nVersion 1.0\n\n" +
            "A tool for scanning and analyzing network hosts.\n" +
            "Created with Java Swing",
            "About Network Scanner",
            JOptionPane.INFORMATION_MESSAGE);
    }
}