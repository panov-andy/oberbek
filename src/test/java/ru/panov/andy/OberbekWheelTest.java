package ru.panov.andy;

import junit.framework.TestCase;

/**
 * @author Andrey Panov
 */
public class OberbekWheelTest extends TestCase {
    public void testGetJwheel() throws Exception {
        //проверка цилинда
        assertEquals(0.005000000000000001d, OberbekWheel.getJCilinder(1d, 0.1d));
        assertEquals(0.0005000000000000001d, OberbekWheel.getJCilinder(0.1d, 0.1d));


        OberbekWheel w = new OberbekWheel(0.05d, 0.35d, 0.3d, 0.15d, 0.2d, 4, 0.23d);
        assertEquals(4.3749999999999995E-4d, OberbekWheel.getJCilinder(w.getValMassa(), w.getValRadius()));
        assertEquals(0.018d, OberbekWheel.getJRods(w.getRodMassa(), w.getRodLenght()));
        assertEquals(0.0023000000000000004d, OberbekWheel.getJDop(w.getDopcargoCount(), w.getDopcargoMassa(), w.getValRadius()));
        assertEquals(0.0207375d, w.getJwheel());

        //угловое ускорение
        assertEquals(4.619187757504416, w.getEwheel());

    }

    public void testGetEwheel() throws Exception {

    }

    public void testGetAtg() throws Exception {

    }

    public void testGetHcargo() throws Exception {
        OberbekWheel w = new OberbekWheel();
        w.setValRadius(0.05d);
        System.out.println("a = " + w.getAtg());
        for (int i = 0; i < 14; i++) {
            System.out.println(w.getHcargo(i));
//            Thread.sleep(1000);
        }

    }
}
