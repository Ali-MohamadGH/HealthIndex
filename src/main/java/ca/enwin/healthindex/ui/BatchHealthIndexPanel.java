package ca.enwin.healthindex.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import ca.enwin.healthindex.model.HealthIndexResult;
import ca.enwin.healthindex.model.ImportResult;
import ca.enwin.healthindex.service.calculation.HealthIndexBatchService;
import ca.enwin.healthindex.service.export.HealthIndexExportService;
import ca.enwin.healthindex.service.imports.InspectionImportService;

public class BatchHealthIndexPanel
        extends JPanel {

    public final JTextArea output =
            new JTextArea();

    public BatchHealthIndexPanel() {
        Color bl = Color.decode("#023C6B");
        Color wh = Color.decode("#FFFFFF");
        Color gr = Color.decode("#31B052");
        Color gr2 = Color.decode("#adceb6");
        Color b1 = Color.decode("#b0c3d1");
        Color b2 = Color.decode("#194d74");
        Font font = new Font("Arial", Font.PLAIN, 14);
        Border grBorder = BorderFactory.createLineBorder(gr, 1);
     
        Border blBorder = BorderFactory.createLineBorder(bl, 2);
        

        setBorder(blBorder);
        setLayout(new BorderLayout());
        setBackground(bl);
        setForeground(wh);
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        GradientButton importButton = new GradientButton(" Import Inspection Export ", b1, b2);
        GradientButton calculateButton = new GradientButton(" Calculate All ", b1, b2);
        GradientButton exportButton = new GradientButton(" Export Results ", b1, b2);

        
        importButton.addActionListener(
                e -> importInspection());

        calculateButton.addActionListener(
                e -> calculate());

        exportButton.addActionListener(
                e -> export());

        

        importButton.setForeground(wh);
        importButton.setFont(font);
   
        calculateButton.setForeground(wh);
        calculateButton.setFont(font);

        exportButton.setForeground(wh);
        exportButton.setFont(font);

        
        
        top.setBackground(bl);
        
        
        
        top.add(importButton);
        top.add(calculateButton);
        top.add(exportButton);

        add(top,
                BorderLayout.NORTH);

        output.setEditable(false);

        add(
                new JScrollPane(output),
                BorderLayout.CENTER);
    }
    
    
        public void outputLogs(String current){
                output.append(current);
                
        }
    

        private void importInspection() {

        JFileChooser chooser =
                new JFileChooser();

        if (chooser.showOpenDialog(this)
                != JFileChooser.APPROVE_OPTION) {
                return;
        }

        File file = chooser.getSelectedFile();

        SwingWorker<ImportResult, String> worker =
                new SwingWorker<>() {

                        @Override
                        protected ImportResult doInBackground()
                                throws Exception {

                        return new InspectionImportService()
                                .importFile(
                                        file,
                                        this::publish);
                        }

                        @Override
                        protected void process(
                                List<String> chunks) {

                        for (String text : chunks) {
                                output.append(text);
                        }
                        }

                        @Override
                        protected void done() {

                        try {

                                ImportResult result = get();

                                output.append(
                                        "\nImported: "
                                                + result.rowsImported()
                                                + "\n");

                        } catch (Exception ex) {

                                ex.printStackTrace();
                        }
                        }
                };

        worker.execute();
        }
    
    private void calculate() {

        try {

            List<HealthIndexResult>
                    results =

                    new HealthIndexBatchService()
                            .calculateAll();

            output.append(

                    "Calculated: "

                            + results.size()

                            + " assets\n");

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    private void export() {
       
        JFileChooser chooser =
                new JFileChooser();
     
        

        if (
                chooser.showSaveDialog(this)
                        != JFileChooser.APPROVE_OPTION
        ) {

            return;
        }
        
        try {

            List<HealthIndexResult>
                    results =

                    new HealthIndexBatchService()
                            .calculateAll();

            File file =
                    (chooser.getSelectedFile());

                    
        if (!file.getName().endsWith(".xlsx")) {
                file = new File(file.getAbsolutePath() + ".xlsx");
        }

            new HealthIndexExportService()
                    .export(
                            file,
                            results);

            output.append(
                    "Exported\n");

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}