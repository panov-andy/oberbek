package ru.panov.andy.examples;

/**
 * @author Andrey Panov
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Paint extends JFrame {

    private Panel pane = new Panel();
    private JButton start = new JButton("Paint");

    public Paint() {
        JFrame mf = new JFrame("Paint Test");
        mf.setLayout(new BorderLayout());
        mf.setSize(500, 500);
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.setBorder(BorderFactory.createLineBorder(Color.RED));
        pane.setBackground(Color.WHITE);
        mf.add(pane, BorderLayout.CENTER);
        mf.add(start, BorderLayout.SOUTH);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                pane.setCoord(5, 5, 400, 400);
                pane.repaint();
            }
        });
        mf.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Paint();
            }
        });
    }


}
