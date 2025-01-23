package com.networkscanner.gui;

import com.networkscanner.model.ModelScanResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ScanResultPanel extends JPanel {
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JTextArea detailsArea;
    private JSplitPane splitPane;

    public ScanResultPanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // Création du modèle de table avec colonnes non modifiables
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Définition des colonnes
        String[] columns = {
            "IP Address",
            "Hostname",
            "MAC Address",
            "OS",
            "Status"
        };
        for (String column : columns) {
            tableModel.addColumn(column);
        }

        // Création de la table
        resultTable = new JTable(tableModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.setAutoCreateRowSorter(true);
        
        // Ajout du tri
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        resultTable.setRowSorter(sorter);

        // Création de la zone de détails
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        // Création du split pane
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(resultTable),
            new JScrollPane(detailsArea));
        splitPane.setResizeWeight(0.7);

        // Ajout de l'écouteur de sélection
        resultTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetails();
            }
        });

        // Configuration du panel principal
        setBorder(BorderFactory.createTitledBorder("Scan Results"));
        add(splitPane, BorderLayout.CENTER);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton clearButton = new JButton("Clear Results");
        clearButton.addActionListener(e -> clearResults());
        
        JButton exportSelectedButton = new JButton("Export Selected");
        exportSelectedButton.addActionListener(e -> exportSelectedResult());
        
        buttonPanel.add(clearButton);
        buttonPanel.add(exportSelectedButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addResult(ModelScanResult result) {
        SwingUtilities.invokeLater(() -> {
            String status = result.isScanSuccessful() ? "Success" : "Failed";
            Object[] row = {
                result.getTargetIp(),
                result.getNetworkInfo() != null ? result.getNetworkInfo().getHostname() : "",
                result.getNetworkInfo() != null ? result.getNetworkInfo().getMacAddress() : "",
                result.getNetworkInfo() != null ? result.getNetworkInfo().getOperatingSystem() : "",
                status
            };
            tableModel.addRow(row);
        });
    }

    private void updateDetails() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Dans un vrai cas, vous récupéreriez l'objet ScanResult complet
            selectedRow = resultTable.convertRowIndexToModel(selectedRow);
            StringBuilder details = new StringBuilder();
            details.append("IP Address: ").append(tableModel.getValueAt(selectedRow, 0)).append("\n");
            details.append("Hostname: ").append(tableModel.getValueAt(selectedRow, 1)).append("\n");
            details.append("MAC Address: ").append(tableModel.getValueAt(selectedRow, 2)).append("\n");
            details.append("Operating System: ").append(tableModel.getValueAt(selectedRow, 3)).append("\n");
            details.append("Status: ").append(tableModel.getValueAt(selectedRow, 4)).append("\n");
            
            detailsArea.setText(details.toString());
        } else {
            detailsArea.setText("");
        }
    }

    private void clearResults() {
        tableModel.setRowCount(0);
        detailsArea.setText("");
    }

    private void exportSelectedResult() {
        int selectedRow = resultTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Dans un vrai cas, vous exporteriez l'objet ScanResult complet
            selectedRow = resultTable.convertRowIndexToModel(selectedRow);
            // Exemple simple d'export
            StringBuilder export = new StringBuilder();
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                export.append(tableModel.getColumnName(i))
                      .append(": ")
                      .append(tableModel.getValueAt(selectedRow, i))
                      .append("\n");
            }
            detailsArea.setText("Export data:\n" + export.toString());
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        resultTable.setEnabled(enabled);
        detailsArea.setEnabled(enabled);
    }
}