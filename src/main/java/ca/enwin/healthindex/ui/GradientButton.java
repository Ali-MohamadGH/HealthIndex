package ca.enwin.healthindex.ui;


import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;




    // 1. Create the custom Gradient Button class
    public class GradientButton extends JButton {
        private Color startColor;
        private Color endColor;

        public GradientButton(String text, Color startColor, Color endColor) {
            super(text);
            this.startColor = startColor;
            this.endColor = endColor;
            
            // Critical steps to let custom painting show through
            setContentAreaFilled(false);
            setFocusPainted(false); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            
            // Enable anti-aliasing for smoother edges if the button is rounded
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create the vertical gradient paint (0, 0 to 0, height)
            GradientPaint gp = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
            g2d.setPaint(gp);
            
            // Draw the background fill
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Always call the super method last to paint text and icon over the background
            super.paintComponent(g);
        }
    }