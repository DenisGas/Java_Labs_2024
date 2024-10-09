package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\denis\\Downloads\\pr2.csv";
        //String filePath = "https://informer.com.ua/dut/java/pr2.csv";
        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        TransactionReportGenerator.printAllData(transactions);

        double totalBalance = TransactionAnalyzer.calculateTotalBalance(transactions);

        System.out.println("-------------------");
        TransactionReportGenerator.printBalanceReport(totalBalance);

        String monthYear = "01-2024";
        int transactionsCount = TransactionAnalyzer.countTransactionsByMonth(monthYear, transactions);
        TransactionReportGenerator.printTransactionsCountByMonth(monthYear, transactionsCount);

        System.out.println("-------------------");

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);
        TransactionReportGenerator.printTopExpensesReport(topExpenses);

        System.out.println("-------------------");

        TransactionReportGenerator.printLargestExpense(transactions, monthYear);

        System.out.println("-------------------");

        TransactionReportGenerator.printSmallestExpense(transactions, monthYear);

        System.out.println("-------------------");

        TransactionReportGenerator.printExpenseSummary(transactions);
    }
}