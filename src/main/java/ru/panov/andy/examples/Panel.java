package ru.panov.andy.examples;

import javax.swing.*;
import java.awt.*;

/**
 * @author Andrey Panov
 */
class Panel extends JPanel {
    public static int x1;
    public static int x2;
    public static int y1;
    public static int y2;

    public static void setCoord(int c1, int c2, int c3, int c4) {
        x1 = c1;
        x2 = c2;
        y1 = c3;
        y2 = c4;
    }

    public Panel() {
        setLayout(null);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(x1, x2, y1, y2);
    }
}
