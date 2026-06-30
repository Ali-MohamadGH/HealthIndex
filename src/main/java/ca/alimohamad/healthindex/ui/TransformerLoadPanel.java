package ca.alimohamad.healthindex.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ca.alimohamad.healthindex.model.TransformerLoad;
import ca.alimohamad.healthindex.repository.TransformerLoadRepository;
import ca.alimohamad.healthindex.service.imports.TransformerLoadImportService;

public class TransformerLoadPanel extends JPanel {

    private final DefaultTableModel model;

    private final TransformerLoadRepository repository
            = new TransformerLoadRepository();

    public TransformerLoadPanel() {
        Color bl = Color.decode("#023C6B");
        Color wh = Color.decode("#FFFFFF");

        Color b1 = Color.decode("#b0c3d1");
        Color b2 = Color.decode("#194d74");
        Font font = new Font("Arial", Font.PLAIN, 14);

        Border blBorder = BorderFactory.createLineBorder(bl, 2);

        setBorder(blBorder);
        setLayout(new BorderLayout());
        setBackground(bl);
        setForeground(wh);

        model = new DefaultTableModel(
                new String[]{
                    "Equipment",
                    "Load Date",
                    "Loading Score"
                },
                0);

        JTable table = new JTable(model);
        JTableHeader header = table.getTableHeader();

        table.setAutoCreateRowSorter(true);

        add(new JScrollPane(table),
                BorderLayout.CENTER);

        GradientButton importButton = new GradientButton(" Import ", b1, b2);
        GradientButton refreshButton = new GradientButton(" Refresh ", b1, b2);

        importButton.addActionListener(
                e -> importData());

        refreshButton.addActionListener(
                e -> loadData());

        JPanel top
                = new JPanel();

        top.add(importButton);
        top.add(refreshButton);

        importButton.setForeground(wh);
        importButton.setFont(font);

        refreshButton.setForeground(wh);
        refreshButton.setFont(font);

        header.setBackground(bl);
        header.setForeground(wh);
        //header.setBorder(grBorder);

        top.setBackground(bl);

        top.add(importButton);
        top.add(refreshButton);
        add(top, BorderLayout.NORTH);

        loadData();
    }

    private void importData() {

        JFileChooser chooser
                = new JFileChooser();

        if (chooser.showOpenDialog(this)
                != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {

            new TransformerLoadImportService()
                    .importFile(
                            chooser.getSelectedFile());

            loadData();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage());
        }
    }

    private void loadData() {

        try {

            model.setRowCount(0);

            for (TransformerLoad load
                    : repository.findAll()) {

                model.addRow(
                        new Object[]{
                            load.equipmentId(),
                            load.loadDate(),
                            load.loadingScore()
                        });
            }

        } catch (SQLException ex) {

        }
    }
}
