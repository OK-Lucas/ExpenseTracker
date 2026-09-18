import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManager employeeManager = new EmployeeManager();
        ExpenseManager expenseManager = new ExpenseManager();

        String currentEmployeeId = login(scanner, employeeManager);
        if (currentEmployeeId == null) {
            System.out.println("Login failed. Goodbye.");
            return;
        }

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                addExpense(scanner, expenseManager, currentEmployeeId);
            } else if (choice.equals("2")) {
                printExpenses(expenseManager.getAllExpenses());
            } else if (choice.equals("3")) {
                filterByMonth(scanner, expenseManager);
            } else if (choice.equals("4")) {
                filterByCategory(scanner, expenseManager);
            } else if (choice.equals("5")) {
                deleteExpense(scanner, expenseManager, currentEmployeeId);
            } else if (choice.equals("0")) {
                running = false;
            } else {
                System.out.println("Not a valid option, try again.");
            }
        }

        System.out.println("Goodbye!");
    }

    private static String login(Scanner scanner, EmployeeManager employeeManager) {
        if (!employeeManager.hasEmployees()) {
            System.out.println("No employees yet. Let's register the first one.");
            System.out.print("New employee ID: ");
            String newId = scanner.nextLine().trim();
            System.out.print("New PIN: ");
            String newPin = scanner.nextLine().trim();
            employeeManager.registerEmployee(newId, newPin);
            System.out.println("Registered employee " + newId + ".");
        }

        System.out.print("Employee ID: ");
        String employeeId = scanner.nextLine().trim();
        System.out.print("PIN: ");
        String pin = scanner.nextLine().trim();

        if (employeeManager.login(employeeId, pin)) {
            System.out.println("Welcome, " + employeeId + "!");
            return employeeId;
        } else {
            return null;
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add expense");
        System.out.println("2. List all expenses");
        System.out.println("3. Filter by month");
        System.out.println("4. Filter by category");
        System.out.println("5. Delete expense");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addExpense(Scanner scanner, ExpenseManager expenseManager, String employeeId) {
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Category: ");
        String category = scanner.nextLine().trim();

        String date = LocalDate.now().toString();

        expenseManager.addExpense(description, amount, category, date, employeeId);
        System.out.println("Expense added.");
    }

    private static void filterByMonth(Scanner scanner, ExpenseManager expenseManager) {
        System.out.print("Enter year and month (e.g. 2026-09): ");
        String monthPrefix = scanner.nextLine().trim();
        printExpenses(expenseManager.filterByMonth(monthPrefix));
    }

    private static void filterByCategory(Scanner scanner, ExpenseManager expenseManager) {
        System.out.print("Category: ");
        String category = scanner.nextLine().trim();
        printExpenses(expenseManager.filterByCategory(category));
    }

    private static void deleteExpense(Scanner scanner, ExpenseManager expenseManager, String employeeId) {
        System.out.print("Expense ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        if (expenseManager.deleteExpense(id, employeeId)) {
            System.out.println("Deleted.");
        } else {
            System.out.println("No expense found with that ID.");
        }
    }

    private static void printExpenses(ArrayList<Expense> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }
}
