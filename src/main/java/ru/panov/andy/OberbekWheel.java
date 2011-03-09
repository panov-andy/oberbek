package ru.panov.andy;

/**
 * Модель маятника обербека.
 * Здесь реализованна вся математика по движению вала при действии силы.
 *
 * @author Andrey Panov
 */
public class OberbekWheel {
    /* Радиус вала, в метрах */
    double valRadius = 0.1d;
    /* Масса вала, в кг */
    double valMassa = 0.5d;

    /* Длинна стержня, в метрах */
    double rodLenght = 0.3d;
    /* Масса срежня */
    double rodMassa = 0.5d;

    /* Масса груза, в кг. */
    double cargoMassa = 0.1d;

    /* Ускорение свободноего падения */
    final public static double g = 9.81d;

    int dopcargoCount = 0; //количество дополнительных грузов
    double dopcargoMassa = 0.1d; //вес дополнительных грузов

    public OberbekWheel() {
    }

    public OberbekWheel(double valRadius, double valMassa, double rodLenght, double rodMassa, double cargoMassa, int dopcargoCount, double dopcargoMassa) {
        this.valRadius = valRadius;
        this.valMassa = valMassa;
        this.rodLenght = rodLenght;
        this.rodMassa = rodMassa;
        this.cargoMassa = cargoMassa;
        this.dopcargoCount = dopcargoCount;
        this.dopcargoMassa = dopcargoMassa;
    }

    /**
     * Момент инерции маховика
     * Момент инерции цилиндра радиуса R, J = 0.5mR^2
     *
     * @return
     */
    public double getJwheel() {
        return getJCilinder(valMassa, valRadius) + getJRods(rodMassa, rodLenght) + getJDop(dopcargoCount, dopcargoMassa, valRadius);
    }

    /**
     * Момент инерции цилинда
     *
     * @param m масса в кг
     * @param R радиус в м.
     * @return
     */
    public static double getJCilinder(double m, double R) {
        return m * R * R / 2d;
    }

    /**
     * момент инерции штырей, 4 штуки
     *
     * @param m
     * @param l
     * @return
     */
    public static double getJRods(double m, double l) {
        return 4 * m * l * l / 3d;
    }

    /**
     * Инерция дополнительных грузов
     *
     * @param count
     * @param m
     * @param R
     * @return
     */
    public static double getJDop(int count, double m, double R) {
        return count * m * R * R;
    }

    /**
     * Угловое ускорение
     * <p/>
     * J*E = T * R
     * J*E = m (g - a) * R
     * J*E = m (g - E*R) * R
     * E = m * g * R / (J + m*R^2)
     *
     * @return
     */
    public double getEwheel() {
        double chis = cargoMassa * g * valRadius;
        double znamen = getJwheel() + cargoMassa * valRadius * valRadius;

        return chis / znamen;
    }

    /**
     * Угловая скорость
     *
     * @return
     */
    public double getWwheel(double t) {
        return getEwheel() * t * t / 2.0d;
    }

    /**
     * Тангенциальное ускорение маховика
     *
     * @return
     */
    public double getAtg() {
        return getEwheel() * valRadius;
    }

    /**
     * Высота груза
     * h = a * t^2 / 2
     *
     * @return
     */
    public double getHcargo(double t) {
        return getAtg() * t * t / 2d;
    }

    public void setValRadius(double valRadius) {
        this.valRadius = valRadius;
    }

    public void setValMassa(double valMassa) {
        this.valMassa = valMassa;
    }

    public void setCargoMassa(double cargoMassa) {
        this.cargoMassa = cargoMassa;
    }

    public int getDopcargoCount() {
        return dopcargoCount;
    }

    public void setDopcargoCount(int dopcargoCount) {
        this.dopcargoCount = dopcargoCount;
    }

    public double getDopcargoMassa() {
        return dopcargoMassa;
    }

    public void setDopcargoMassa(double dopcargoMassa) {
        this.dopcargoMassa = dopcargoMassa;
    }

    public double getValRadius() {
        return valRadius;
    }

    public double getValMassa() {
        return valMassa;
    }

    public double getRodLenght() {
        return rodLenght;
    }

    public double getRodMassa() {
        return rodMassa;
    }

    public double getCargoMassa() {
        return cargoMassa;
    }

    public static double getG() {
        return g;
    }
}
