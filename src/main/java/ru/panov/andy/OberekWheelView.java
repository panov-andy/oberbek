package ru.panov.andy;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * @author Andrey Panov
 */
public class OberekWheelView extends JPanel implements Runnable {
    private OberbekWheel wheelModel;
    long start; //начало эксперимента
    double elapsedTime = 0; //продолжительность эсперимента

    boolean inprogress = false;

    public void setInprogress(boolean inprogress) {
        this.inprogress = inprogress;
    }

    public boolean isInprogress() {
        return inprogress;
    }

    public void run() {
        start = System.currentTimeMillis();
        inprogress = true;

        while (inprogress) {
            elapsedTime = start - System.currentTimeMillis();
            repaint();
            try {
                Thread.sleep(40);
                angel += 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public OberekWheelView(OberbekWheel wheelModel) {
        this.wheelModel = wheelModel;

        this.dopcargoRadiusLen = getDopCargoRadius(); //переводим в местное измерение, в пиксели
        this.shkifDiam = getShkifDiam();

        initWheel();
        initCargo();
        initLine2();
    }

    GeneralPath path;

    int x = 100; //координаты центра
    int y = 100; //координаты центра
    int valDiam = 20; //диаметр вала
    int shkifDiam; //диаметр шкива
    int spicaLen = 80;
    int dopcargoRadiusLen;
    double angel = 0;

    int gh = 10; //высота груза
    int gl = 20; //ширина груза

    int scaleLen = 110; //длинна шкалы, см
    int scaleOffset = 25; //отступ шкалы сверху и снизу

    int cargoX = x + 80;
    int carcoL = 30;
    double cargoH; //высота в сантимертарах, на которой находится груз

    Ellipse2D.Double shkif;
    Ellipse2D.Double center;
    Ellipse2D.Double center2;
    Rectangle cargo; //груз
    GeneralPath scale; //шкала
    Line2D.Double line1; //веревочка
    Line2D.Double line2; //веревочка

    public void initWheel() {

        path = new GeneralPath();

        Line2D point = new Line2D.Double(x, y, x, y);
        path.append(point, false);

        //координаты - верхний левый угол, если эллипс в центре прямоугольника
        center = new Ellipse2D.Double(x - valDiam / 2, y - valDiam / 2, valDiam, valDiam);
        center2 = new Ellipse2D.Double(cargoX + carcoL / 2 - 10, scaleOffset - 20, 10, 10);

        //нужно исключительно, что выяснить точку прикрепления нити к шкиву
        Rectangle temppoint = new Rectangle(x,y- getShkifDiam() / 2,1,1);
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(-30),x,y);
        Shape tp = tx.createTransformedShape(temppoint);

        shkif = new Ellipse2D.Double(x - getShkifDiam() / 2, y - getShkifDiam() / 2, getShkifDiam(), getShkifDiam());
//        line1 = new Line2D.Double(x - getShkifDiam() / 2 + 4.5, y - getShkifDiam() / 2 + 4.5, cargoX + carcoL / 2 - 10 + 1.5, scaleOffset - 20 + 1.5);
        line1 = new Line2D.Double(tp.getBounds().getCenterX(), tp.getBounds().getCenterY(), cargoX + carcoL / 2 - 10 + 1.5, scaleOffset - 20 + 1.5);


        //стержень вверх
        Line2D linup = new Line2D.Double(x, y - valDiam / 2, x, y - spicaLen);
        path.append(linup, false);

        Rectangle recup = new Rectangle(x - gl / 2, y - getDopCargoRadius() - gh, gl, gh);
        if (wheelModel.getDopcargoCount() > 0)
            path.append(recup, false);

        //стержень вних
        Line2D lindown = new Line2D.Double(x, y + valDiam / 2, x, y + spicaLen);
        path.append(lindown, false);

        //вес снизу
        Rectangle recdown = new Rectangle(x - gl / 2, y + getDopCargoRadius(), gl, gh);
        if (wheelModel.getDopcargoCount() > 0)
            path.append(recdown, false);

        //стержень влево
        Line2D linleft = new Line2D.Double(x - valDiam / 2, y, x - spicaLen, y);
        path.append(linleft, false);

        //вес слева
        Rectangle recleft = new Rectangle(x - getDopCargoRadius() - gh, y - gl / 2, gh, gl);
        if (wheelModel.getDopcargoCount() > 2)
            path.append(recleft, false);

        //стержень вправо
        Line2D linrig = new Line2D.Double(x + valDiam / 2, y, x + spicaLen, y);
        path.append(linrig, false);

        //вес справа
        Rectangle recrig = new Rectangle(x + getDopCargoRadius(), y - gl / 2, gh, gl);
        if (wheelModel.getDopcargoCount() > 2)
            path.append(recrig, false);
    }

    private void initCargo() {
        cargo = new Rectangle(cargoX, (int) 0, carcoL, 15);
    }

    private void initLine2() {
        //линия, которая идет к грузу
        line2 = new Line2D.Double(cargoX + carcoL / 2, scaleOffset - 15, cargoX + carcoL / 2, h2x(cargoH));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(1.0f)); //толщина линии

        /* ----------------- Рисуем шкалу ----------------- */
        int baseX = x + 150;
        int baseY = y - 75;
        int lenX = 20;
        //сверху и снизу отступаем одинаково
        double h1 = this.getSize().getHeight() - scaleOffset * 2;

        scale = new GeneralPath();
        //каждые 10

        for (int i = 0; i <= scaleLen; i++) {
            if (i % 10 == 0) {
                scale.append(new Line2D.Double(baseX - 20, h2x(i), baseX + lenX, h2x(i)), false);
                g2.drawString(i + " см", baseX + lenX + 3, (int) h2x(i));
            } else if (i % 5 == 0) {
                scale.append(new Line2D.Double(baseX - 10, h2x(i), baseX + lenX, h2x(i)), false);
            } else {
                scale.append(new Line2D.Double(baseX, h2x(i), baseX + lenX, h2x(i)), false);
            }
        }
        /* ----------------- Рисуем шкалу ----------------- */

        AffineTransform Tx = new AffineTransform();
        Tx.rotate(wheelModel.getWwheel(elapsedTime / 1000.0d), x, y);
        Shape shape = Tx.createTransformedShape(path);

        GeneralPath temp = new GeneralPath();
        temp.append(shape, false);
        temp.append(center, false);
        temp.append(center2, false);
        temp.append(shkif, false);
        temp.append(line1, false);

        g2.draw(temp);
        g2.draw(getCargo());
        g2.draw(scale);
        initLine2(); //проще создать целиком
        g2.draw(line2);
    }

    /**
     * перевод высоты в пиксели
     *
     * @param h высота в сантиметрах
     * @return
     */
    private double h2x(double h) {
        double height = this.getBounds().getHeight() - scaleOffset * 2;
        double scal = height / (double) scaleLen;
        return h * scal + scaleOffset;
    }

    private static double h2x(double screenH, double h, int lenth) {
        double v = screenH / (double) lenth;
        return h * v;
    }

    private Shape getCargo() {
        cargoH = wheelModel.getHcargo(elapsedTime / 1000.0d) * 100; //переводим в сантиметры

        double v = h2x(cargoH);
        if (v + scaleOffset > this.getBounds().getHeight()) {
            inprogress = false;
            cargoH = scaleLen;
        }

        AffineTransform Tx = new AffineTransform();
        Tx.translate(0, h2x(cargoH));
        return Tx.createTransformedShape(cargo);
    }

    public void resetState() {
        elapsedTime = 0;
        inprogress = false;
    }

    private int getDopCargoRadius() {
        //переводим метры в пиксели
        return (int) (wheelModel.getDopcargoRadius() * 200);
    }

    public int getShkifDiam() {
        //переводим метры в пиксели, на 400 т.к. нужен диаметр
        return (int) (wheelModel.getValRadius() * 400);
    }
}
