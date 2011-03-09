package ru.panov.andy;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

/**
 * @author Andrey Panov
 */
public class Stand extends JPanel implements Runnable {
    int degree = 0;
    Val val = new Val();

    public Stand() {
        setDoubleBuffered(true);
        new Thread(this).start();
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(2.0f)); //толщина линии

        g2.drawLine(150, 0, 45, 45);


        Rectangle rect = new Rectangle(50, 50, 57, 33);
        Rectangle rect2 = new Rectangle(160, 60, 75, 75);

        //SHAPE
        GeneralPath p = new GeneralPath();
        p.append(rect,false);
        p.append(rect2,false);
        g2.draw(p);


        AffineTransform Tx = new AffineTransform();
        Tx.rotate(Math.toRadians(degree), 150,150);
        GeneralPath path = new GeneralPath();

//        path.append(Tx.createTransformedShape(rect), false);
        Tx.translate(16, 14);

        Shape shape = Tx.createTransformedShape(rect);
        Shape shape2 = Tx.createTransformedShape(p);

//        g2.draw(rect);
//        g2.draw(shape);
//        g2.draw(shape2);
        g2.draw(val.getCrest());



        if (degree == 360) {
            degree = 1;
        } else {
            degree++;
        }
    }
}
