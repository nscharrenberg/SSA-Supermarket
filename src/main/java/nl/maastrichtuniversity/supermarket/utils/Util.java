package nl.maastrichtuniversity.supermarket.utils;

public class Util {
    public static double drawRandomExponential(double mean) {
        // Draw a [0, 1] uniform distributed number
        double unif = Math.random();

        // Convert it into an exponentially distributed random variate with mean 33
        return -mean * Math.log(unif);
    }
}
