package org.example;

import java.util.Comparator;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public abstract class TransactionAnalyzer {

    // Метод для розрахунку загального балансу
    public static double calculateTotalBalance(List<Transaction> transactions) {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }

    // Метод для підрахунку транзакцій за конкретний місяць і рік
    public static int countTransactionsByMonth(String monthYear, List<Transaction> transactions) {
        int count = 0;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Transaction transaction : transactions) {
            LocalDate date = LocalDate.parse(transaction.getDate(), dateFormatter);
            String transactionMonthYear = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }
        return count;
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0) // Вибірка лише витрат (від'ємні значення)
                .sorted(Comparator.comparing(Transaction::getAmount)) // Сортування за сумою
                .limit(10) // Обмеження результату першими 10 записами
                .collect(Collectors.toList()); // Збір результату в список
    }

    public static Transaction findExpense(List<Transaction> transactions, String monthYear, boolean findLargest) {
        Transaction resultExpense = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM-yyyy");

        for (Transaction transaction : transactions) {
            // Перевірка на витрати
            if (transaction.getAmount() < 0) {
                LocalDate transactionDate = LocalDate.parse(transaction.getDate(), dateFormatter);
                String transactionMonthYear = transactionDate.format(monthYearFormatter);

                // Якщо місяць і рік збігатися
                if (transactionMonthYear.equals(monthYear)) {
                    // Логіка для знаходження найбільшої або найменшої витрати
                    if (resultExpense == null ||
                            (findLargest && transaction.getAmount() < resultExpense.getAmount()) ||
                            (!findLargest && transaction.getAmount() > resultExpense.getAmount())) {
                        resultExpense = transaction;
                    }
                }
            }
        }

        return resultExpense; // Повертаємо або null, якщо витрат не знайдено
    }

    // Метод для знаходження найбільшої витрати за вказаний місяць
    public static Transaction findLargestExpense(List<Transaction> transactions, String monthYear) {
        return findExpense(transactions, monthYear, true);
    }

    // Метод для знаходження найменшої витрати за вказаний місяць
    public static Transaction findSmallestExpense(List<Transaction> transactions, String monthYear) {
        return findExpense(transactions, monthYear, false);
    }
}
