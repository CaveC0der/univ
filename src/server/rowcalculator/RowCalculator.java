package server.rowcalculator;

public class RowCalculator {
    private static long factorial(int n) {
        if (n < 0)
            throw new ArithmeticException("n < 0");
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static double calculate(double a, double b, double x, double y) {
        double result = 1;
        double numerator = Math.log(x) + b * y + a * Math.pow(x, 2);
        for (int i = 0; i <= 10; i++) {
            result *= Math.pow(-1, i) * numerator / (factorial(i) * Math.E);
        }
        return result;
    }
}
