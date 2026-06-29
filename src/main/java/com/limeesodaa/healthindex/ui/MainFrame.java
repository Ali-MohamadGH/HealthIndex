package com.limeesodaa.healthindex.ui;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
public class MainFrame extends JFrame {

    public MainFrame() {
       
        Color bl = Color.decode("#023C6B");
        Color wh = Color.decode("#FFFFFF");
        Color gr = Color.decode("#31B052");
      
        Font font = new Font("Arial", Font.PLAIN, 14);

        setTitle(
                "PMH Health Index");

        setSize(1400, 800);
        setBackground(bl);
        setForeground(gr);
         
            // Load from src/main/resources/icon.png
        this.setIconImage(new ImageIcon(getClass().getResource("/PME.png")).getImage());
        

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
        
        setLocationRelativeTo(null);
                
        JTabbedPane tabs =
                new JTabbedPane();

        tabs.addTab(
                "Categories",
                new CategoryPanel());
        tabs.addTab(
                "Conversion", new ConversionPanel());
        tabs.addTab(
                "Weights",
                new WeightPanel());

        

        tabs.addTab(
                "Batch Health Index",
                new BatchHealthIndexPanel());

        tabs.addTab(
                "Results",
                new ResultsPanel());
        
        tabs.setBackground(bl);
        tabs.setForeground(wh);
        tabs.setFont(font);
        add(tabs);
    }
}