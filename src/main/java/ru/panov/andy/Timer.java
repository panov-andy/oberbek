package ru.panov.andy;

import javax.swing.*;
import java.text.SimpleDateFormat;

/**
 * Исключительно для обновления часов
 *
 * @author Andrey Panov
 */
public class Timer implements Runnable {
    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss   SSS");

    JLabel time;

    public Timer(JLabel time) {
        this.time = time;
    }

    public void run() {
        while (true) {
            String format = df.format(System.currentTimeMillis());
            time.setText(format.substring(0, format.length() - 2) + "'");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
        }
    }

}
