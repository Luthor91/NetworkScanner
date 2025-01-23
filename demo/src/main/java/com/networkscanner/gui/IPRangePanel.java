package com.networkscanner.gui;

import com.networkscanner.utils.NetworkUtils;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IPRangePanel extends JPanel {
    private DefaultListModel<IPRangeItem> listModel;
    private JList<IPRangeItem> ipList;

    public IPRangePanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Available IP Ranges"));
        initComponents();
        refreshIPRanges();
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        ipList = new JList<>(listModel);
        ipList.setCellRenderer(new IPRangeRenderer());
        ipList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        // Ajouter la gestion des clics sur les éléments de la liste
        ipList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = ipList.locationToIndex(evt.getPoint());
                if (index != -1) {
                    IPRangeItem item = listModel.getElementAt(index);
                    item.setSelected(!item.isSelected());
                    ipList.repaint();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(ipList);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshIPRanges());
        
        JButton selectAllButton = new JButton("Select All");
        selectAllButton.addActionListener(e -> selectAll(true));
        
        JButton deselectAllButton = new JButton("Deselect All");
        deselectAllButton.addActionListener(e -> selectAll(false));
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(selectAllButton);
        buttonPanel.add(deselectAllButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void refreshIPRanges() {
        listModel.clear();
        List<String> ranges = NetworkUtils.getLocalIPRanges();
        for (String range : ranges) {
            listModel.addElement(new IPRangeItem(range));
        }
    }

    private void selectAll(boolean selected) {
        for (int i = 0; i < listModel.size(); i++) {
            listModel.getElementAt(i).setSelected(selected);
        }
        ipList.repaint();
    }

    public List<String> getSelectedIPs() {
        List<String> selectedIPs = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            IPRangeItem item = listModel.getElementAt(i);
            if (item.isSelected()) {
                String[] range = item.getRange().split(" - ");
                selectedIPs.addAll(NetworkUtils.generateIPRange(range[0], range[1]));
            }
        }
        return selectedIPs;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        ipList.setEnabled(enabled);
        for (Component comp : getComponents()) {
            comp.setEnabled(enabled);
        }
    }

    // Classe pour représenter un élément de la liste
    private static class IPRangeItem {
        private String range;
        private boolean selected;

        public IPRangeItem(String range) {
            this.range = range;
            this.selected = false;
        }

        public String getRange() { return range; }
        public boolean isSelected() { return selected; }
        public void setSelected(boolean selected) { this.selected = selected; }
        
        @Override
        public String toString() {
            return range;
        }
    }

    // Renderer personnalisé pour afficher les cases à cocher
    private static class IPRangeRenderer extends JCheckBox implements ListCellRenderer<IPRangeItem> {
        public IPRangeRenderer() {
            setBackground(UIManager.getColor("List.background"));
            setForeground(UIManager.getColor("List.foreground"));
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends IPRangeItem> list,
                IPRangeItem value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            
            setText(value.getRange());
            setSelected(value.isSelected());
            
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            
            return this;
        }
    }
}