package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            String operation = getOperationInput(scanner);

            double result;
            if (operation.equals("sqrt")) {
                
                double a = getDoubleInput(scanner, "Введіть число для обчислення квадратного кореня: ");
                result = Calculator.squareRoot(a);
            } else {
                
                double a = getDoubleInput(scanner, "Введіть перше число: ");
                double b = getDoubleInput(scanner, "Введіть друге число: ");

                
                result = switch (operation) {
                    case "+" -> Calculator.sum(a, b);
                    case "-" -> Calculator.difference(a, b);
                    case "*" -> Calculator.multiplication(a, b);
                    case "/" -> Calculator.division(a, b);
                    default -> throw new InvalidInputException("Невірна операція: " + operation);
                };
            }

            System.out.println("Результат: " + result);

        } catch (ArithmeticException | InvalidInputException e) {
            System.out.println("Помилка: " + e.getMessage());
        } finally {
            System.out.println("Дякуємо за використання калькулятора!");
        }
    }


    private static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Помилка: введено некоректне значення. Будь ласка, введіть число.");
                scanner.next();
            }
        }
    }

    private static String getOperationInput(Scanner scanner) throws InvalidInputException {
        System.out.print("Виберіть операцію (+, -, *, /, sqrt): ");

        String operation = scanner.next().toLowerCase();
        return switch (operation) {
            case "+", "-", "*", "/", "sqrt" -> operation;
            default -> throw new InvalidInputException("Невірна операція: " + operation);
        };
    }

}
