package ru.panov.andy.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class PulseCircle extends JComponent implements ActionListener {
  private double scale;
  private double factor;
  private Color color;
  private Timer timer;

  public PulseCircle(Color color, int delay) {
    scale = 1.0;
    factor = 1.1;
    timer = new Timer(delay, this);
    this.color = color;
    setPreferredSize(new Dimension(50, 50));
  }

  public void start() {
    timer.start();
  }

  public void stop() {
    timer.stop();
  }

  public void actionPerformed(ActionEvent arg0) {
    final double minScale = 0.1;
    final double maxScale = 1.0;
    scale *= factor;
    if (scale > maxScale) {
      scale = maxScale;
      factor = 1.0 / factor;
    }
    if (scale < minScale) {
      scale = minScale;
      factor = 1.0 / factor;
    }
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.white);
    int width = getWidth();
    int height = getHeight();
    g.fillRect(0, 0, width, height);
    g2d.setColor(Color.black);
    g2d.drawRect(0, 0, width - 1, height - 1);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setColor(color);
    double dx = 0.5 * width;
    double dy = 0.5 * height;
    g2d.translate(dx, dy);
    g2d.scale(scale, scale);
    double x = -dx + 1;
    double y = -dy + 1;
    Ellipse2D el = new Ellipse2D.Double(x, y, width - 2, height - 2);
    g2d.fill(el);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame("Pulse Circle");
        JPanel panel = new JPanel();
        final PulseCircle pulseCircleBlue = new PulseCircle(Color.blue, 50);
        final PulseCircle pulseCircleRed = new PulseCircle(Color.red, 25);
        panel.add(pulseCircleBlue);
        panel.add(pulseCircleRed);
        frame.getContentPane().add(panel);
        final JButton button = new JButton("Start");
        button.addActionListener(new ActionListener() {
          private boolean pulsing = false;

            public void actionPerformed(ActionEvent e) {
            if (pulsing) {
              pulsing = false;
              pulseCircleBlue.stop();
              pulseCircleRed.stop();
              button.setText("Start");
            } else {
              pulsing = true;
              pulseCircleBlue.start();
              pulseCircleRed.start();
              button.setText("Stop");
            }
          }
        });
        frame.add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 150);
        frame.setVisible(true);
      }
    });
  }
}
