package ca.alimohamad.healthindex.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ca.alimohamad.healthindex.model.HealthIndexResult;
import ca.alimohamad.healthindex.repository.HealthIndexResultRepository;

public class ResultsPanel extends JPanel {

    private final DefaultTableModel model;

    private final HealthIndexResultRepository repository
            = new HealthIndexResultRepository();

    public ResultsPanel() {
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
                    "Inspection Date",
                    "Health Index",
                    "Gateway Fail"
                },
                0
        );

        JTable table = new JTable(model);
        JTableHeader header = table.getTableHeader();

        table.setAutoCreateRowSorter(true);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER);

        GradientButton refresh = new GradientButton(" Refresh ", b1, b2);

        refresh.addActionListener(
                e -> loadData());

        JPanel top
                = new JPanel();

        top.add(refresh);

        refresh.setForeground(wh);
        refresh.setFont(font);

        header.setBackground(bl);
        header.setForeground(wh);
        //header.setBorder(grBorder);

        top.setBackground(bl);

        top.add(refresh);
        add(top,
                BorderLayout.NORTH);

        loadData();
    }

    private void loadData() {

        try {

            model.setRowCount(0);

            for (HealthIndexResult result
                    : repository.findAll()) {

                model.addRow(
                        new Object[]{
                            result.equipmentId(),
                            result.inspectionDate(),
                            String.format("%.2f", result.healthIndex() * 100.00) + "%",
                            result.gatewayFail()
                        });
            }

        } catch (SQLException ex) {

        }
    }
}
