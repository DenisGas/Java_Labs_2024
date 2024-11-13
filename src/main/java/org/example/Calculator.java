package org.example;

public class Calculator {

    public static double sum(double a, double b) {
        return a + b;
    }

    public static double difference(double a, double b) {
        return a - b;
    }

    public static double multiplication(double a, double b) {
        return a * b;
    }

    public static double division(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Ділення на нуль неможливе.");
        }
        return a / b;
    }

    public static double squareRoot(double a) throws InvalidInputException {
        if (a < 0) {
            throw new InvalidInputException("Неможливо обчислити квадратний корінь з від'ємного числа.");
        }
        return Math.sqrt(a);
    }
}
