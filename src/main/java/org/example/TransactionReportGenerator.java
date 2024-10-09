package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TransactionReportGenerator {

    static String valueSymbol = "₴";

    public static void printAllData(List<Transaction> transactions){
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance + valueSymbol);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount() + valueSymbol);
        }
    }
    public static void printExpenseSummary(List<Transaction> transactions) {
        Map<String, Double> categoryTotals = new HashMap<>();
        Map<String, Double> monthTotals = new HashMap<>();

        // Обчислення сумарних витрат по категоріях і місяцях
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {  // Тільки витрати
                // Сумарні витрати по категоріях
                categoryTotals.merge(transaction.getDescription(), Math.abs(transaction.getAmount()), Double::sum);

                // Сумарні витрати по місяцях
                String month = transaction.getDate().substring(3, 7) + "-" + transaction.getDate().substring(6, 10); // Формат MM-yyyy
                monthTotals.merge(month, Math.abs(transaction.getAmount()), Double::sum);
            }
        }

        // Виведення звіту по категоріях
        System.out.println("Сумарні витрати по категоріях:");
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double total = entry.getValue();
            System.out.println(category + ": " + total + valueSymbol + " " + generateStars(total));
        }

        // Виведення звіту по місяцях
        System.out.println("\nСумарні витрати по місяцях:");
        for (Map.Entry<String, Double> entry : monthTotals.entrySet()) {
            String month = entry.getKey();
            double total = entry.getValue();
            System.out.println(month + ": " + total + valueSymbol + " " + generateStars(total));
        }
    }

    public static void printLargestExpense(List<Transaction> transactions, String monthYear) {
        Transaction largestExpense = TransactionAnalyzer.findLargestExpense(transactions, monthYear);
        if (largestExpense != null) {
            System.out.println("Найбільша витрата за " + monthYear + ": " + largestExpense.getAmount() + valueSymbol + " (" + largestExpense.getDescription() + ")");
        } else {
            System.out.println("Немає витрат за " + monthYear);
        }
    }

    public static void printSmallestExpense(List<Transaction> transactions, String monthYear) {
        Transaction smallestExpense = TransactionAnalyzer.findSmallestExpense(transactions, monthYear);
        if (smallestExpense != null) {
            System.out.println("Найменша витрата за " + monthYear + ": " + smallestExpense.getAmount() + valueSymbol + " (" + smallestExpense.getDescription() + ")");
        } else {
            System.out.println("Немає витрат за " + monthYear);
        }
    }


    private static String generateStars(double total) {
        int starsCount = (int) (total / 1000); // Кожна зірка = 1000 грн
        return "*".repeat(starsCount);
    }
}
