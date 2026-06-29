package com.limeesodaa.healthindex.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import com.limeesodaa.healthindex.model.HealthIndexResult;
import com.limeesodaa.healthindex.model.ImportResult;
import com.limeesodaa.healthindex.service.calculation.HealthIndexBatchService;
import com.limeesodaa.healthindex.service.export.HealthIndexExportService;
import com.limeesodaa.healthindex.service.imports.InspectionImportService;

public class BatchHealthIndexPanel
        extends JPanel {

    public final JTextArea output =
            new JTextArea();

    public BatchHealthIndexPanel() {
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

                        } catch (InterruptedException | ExecutionException ex) {
                        }
                        }
                };

        worker.execute();
        }
    
        private void calculate() {

        SwingWorker<List<HealthIndexResult>, String>
                worker =
                new SwingWorker<>() {

                        @Override
                        protected List<HealthIndexResult>
                        doInBackground()
                                throws Exception {

                        return new HealthIndexBatchService()
                                .calculateAll(
                                        this::publish);
                        }

                        @Override
                        protected void process(
                                List<String> chunks) {

                        for (String text :
                                chunks) {

                                output.append(
                                        text);
                        }
                        }

                        @Override
                        protected void done() {

                        try {

                                List<HealthIndexResult>
                                        results =
                                        get();

                                output.append(

                                        "\nCalculated: "

                                                + results.size()

                                                + " assets\n");

                        } catch (InterruptedException | ExecutionException ex) {
                        }
                        }
                };

        worker.execute();
        }

    private void export() {

    JFileChooser chooser =
            new JFileChooser();

    if (chooser.showSaveDialog(this)

            != JFileChooser.APPROVE_OPTION) {

        return;
    }

    File selectedFile =
            chooser.getSelectedFile();

    SwingWorker<Void, String>
            worker =
            new SwingWorker<>() {

                @Override
                protected Void doInBackground()
                        throws Exception {

                    publish(
                            "Calculating results...\n");

                    List<HealthIndexResult>
                            results =

                            new HealthIndexBatchService()
                                    .calculateAll(
                                            this::publish);

                    File file =
                            selectedFile;

                    if (!file.getName()
                            .endsWith(
                                    ".xlsx")) {

                        file = new File(

                                file.getAbsolutePath()

                                        + ".xlsx");
                    }

                    new HealthIndexExportService()

                            .export(

                                    file,

                                    results,

                                    this::publish);

                    return null;
                }

                @Override
                protected void process(
                        List<String> chunks) {

                    for (String text :
                            chunks) {

                        output.append(
                                text);
                    }
                }

                @Override
                protected void done() {

                    output.append(
                            "\nExported\n");
                }
            };

    worker.execute();
}}