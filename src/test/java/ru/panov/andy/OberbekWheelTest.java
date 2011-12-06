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


        OberbekWheel w = new OberbekWheel(0.05d, 0.35d, 0.3d, 0.15d, 0.2d, 4, 0.23d, 0.05d);
        assertEquals(4.3749999999999995E-4d, OberbekWheel.getJCilinder(w.getValMassa(), w.getValRadius()));
        assertEquals(0.018d, OberbekWheel.getJRods(w.getRodMassa(), w.getRodLenght()));
        assertEquals(0.0023000000000000004d, OberbekWheel.getJDop(w.getDopcargoCount(), w.getDopcargoMassa(), w.getDopcargoRadius()));
        assertEquals(0.0207375d, w.getJwheel());
        assertEquals(4.619187757504416d, w.getEwheel());
        assertEquals(0.2309593878752208d, w.getAtg());
        assertEquals(0.4619187757504416d, w.getHcargo(2.0d));
        assertEquals(0.09579040612124783d, w.getMoment(2.0d));
        //угловое ускорение
        assertEquals(4.619187757504416, w.getEwheel());


    }

    /**
     * Повторная проверка с другими значениями
     */
    public void testVals2() {
        //радиус шкива = 0.15 метра
        //масса шкива = 0.3 кг
        //длина стержня 0.4 метра
        //масса одного стержня = 0.05 кг
        //масса подвешенного груза 0.45 кг
        //количество грузов на стержнях = 2
        //масса одного груза 0.65 кг
        //радиус расположения груза на стержне 0.2 метра
        OberbekWheel w = new OberbekWheel(0.15d, 0.3d, 0.4d, 0.05d, 0.45d, 2, 0.65, 0.2d);

        //момент инерции шкива (вала \ цилиндра)
        assertEquals(0.003375d, OberbekWheel.getJCilinder(w.getValMassa(), w.getValRadius()));

        //момент инерции четырех стержней
        assertEquals(0.01066666666666667, OberbekWheel.getJRods(w.getRodMassa(), w.getRodLenght()));

        //момент инерции дополнительных грузов
        assertEquals(0.052000000000000005d, OberbekWheel.getJDop(w.getDopcargoCount(), w.getDopcargoMassa(), w.getDopcargoRadius()));

        //момент инреции маятника целиком
        assertEquals(0.06604166666666668d, w.getJwheel());

        //угловое ускорение маятника при данном грузе
        assertEquals(8.693763676148796d, w.getEwheel());

        //тангенциальное ускорение
        assertEquals(1.3040645514223195, w.getAtg());

        //высота падения груза за 2 секунды
        assertEquals(2.608129102844639, w.getHcargo(2.0d));

        //момент силы
        assertEquals(0.5741506427789935d, w.getMoment(2.0d));

        //угловое ускорение
        assertEquals(8.693763676148796, w.getEwheel());
    }

    public void testPart1() throws Exception {
        //контроль выполнения
        OberbekWheel w1 = new OberbekWheel(0.05d, 0.35d, 0.3d, 0.15d, 0.2d, 4, 0.23d, 0.05d);
        double e1 = w1.getEwheel();
        double M1 = w1.getMoment(2.5d);
        System.out.println("e1 = " + e1 + ", M1 = " + M1);

        //масса груза 0.5
        OberbekWheel w2 = new OberbekWheel(0.05d, 0.35d, 0.3d, 0.15d, 0.5d, 4, 0.23d, 0.05d);
        double e2 = w2.getEwheel();
        double M2 = w2.getMoment(2.5d);
        System.out.println("e2 = " + e2 + ", M2 = " + M2);

        System.out.println(e2 / e1 + " ?= " + M2 / M1);


    }

    public void testPart2() throws Exception {
        //проверка второй части лабораторной
        OberbekWheel w1 = new OberbekWheel(0.05d, 0.35d, 0.3d, 0.15d, 0.5d, 2, 0.23d, 0.05d);
        double e1 = w1.getEwheel();
        double J1 = w1.getJwheel();

        OberbekWheel w2 = new OberbekWheel(0.05d, 0.35d, 0.3d, 0.15d, 0.5d, 4, 0.23d, 0.05d);
        double e2 = w2.getEwheel();
        double J2 = w2.getJwheel();

        System.out.println(e2 / e1 + " ?= " + J1 / J2);
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
