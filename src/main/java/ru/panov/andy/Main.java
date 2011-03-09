/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.panov.andy;

import javax.swing.*;

/**
 * @author aware
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MFrame main = new MFrame();
                main.createBufferStrategy(2); //не работает кажется                
                main.setVisible(true);
            }
        });
    }

}
