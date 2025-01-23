package com.networkscanner;

import com.networkscanner.gui.MainWindow;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch GUI in Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}