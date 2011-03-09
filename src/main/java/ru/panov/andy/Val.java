package ru.panov.andy;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

/**
 * @author Andrey Panov
 */
public class Val implements Runnable {
    public Val() {
        init();
        new Thread(this).start();
    }

    public void run() {
        while (true) {
            try {
                angel += 360 / 360 * 10 / internalStep;
                Thread.sleep(internalStep);
            } catch (InterruptedException e) {

            }
        }
    }

    GeneralPath path;

    int x = 200; //координаты центра
    int y = 200; //координаты центра
    int speed = 0;
    int valDiam = 30; //диаметр вала
    int spicaLen = 60;
    double angel = 0;

    int gh = 10; //высота груза
    int gl = 20; //ширина груза

    int internalStep = 10; //внутренний таймер, нужен для перехода в секунды

    Ellipse2D.Double center;

    private void init() {

        path = new GeneralPath();

        Line2D point = new Line2D.Double(x, y, x, y);
        path.append(point, false);

        //координаты - верхний левый угол, если эллипс в центре прямоугольника

        center = new Ellipse2D.Double(x - valDiam / 2, y - valDiam / 2, valDiam, valDiam);
//        path.append(center, false);

        //стержень вверх
        Line2D linup = new Line2D.Double(x, y - valDiam / 2, x, y - spicaLen);
        path.append(linup, false);

        Rectangle recup = new Rectangle(x - gl / 2, y - spicaLen - gh, gl, gh);
        path.append(recup, false);

        //стержень вних
        Line2D lindown = new Line2D.Double(x, y + valDiam / 2, x, y + spicaLen);
        path.append(lindown, false);

        //вес снизу
        Rectangle recdown = new Rectangle(x - gl / 2, y + spicaLen, gl, gh);
        path.append(recdown, false);

        //стержень влево
        Line2D linleft = new Line2D.Double(x - valDiam / 2, y, x - spicaLen, y);
        path.append(linleft, false);

        //вес слева
        Rectangle recleft = new Rectangle(x - spicaLen - gh, y-gl/2, gh, gl);
        path.append(recleft, false);

        //стержень вправо
        Line2D linrig = new Line2D.Double(x + valDiam / 2, y, x + spicaLen, y);
        path.append(linrig, false);

        //вес справа
        Rectangle recrig = new Rectangle(x + spicaLen, y-gl/2, gh, gl);
        path.append(recrig, false);


    }

    public Shape getCrest() {
        AffineTransform Tx = new AffineTransform();
        Tx.rotate(Math.toRadians(angel), x, y);
        Shape shape = Tx.createTransformedShape(path);

        GeneralPath temp = new GeneralPath();
        temp.append(shape, false);
        temp.append(center, false);

        return temp;
    }
}
