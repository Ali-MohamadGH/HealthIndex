package com.limeesodaa.healthindex;

import javax.swing.SwingUtilities;

import com.limeesodaa.healthindex.database.DatabaseInitializer;
import com.limeesodaa.healthindex.ui.MainFrame;

public class Main {

    public static void main(String[] args) {

        try {

            DatabaseInitializer initializer
                    = new DatabaseInitializer();

            initializer.initialize();

            SwingUtilities.invokeLater(() -> {

                MainFrame frame
                        = new MainFrame();

                frame.setVisible(true);
            });

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
