import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Keeps the list of expenses in memory and saves/loads them from a text file.
public class ExpenseManager {
    private static final String FILE_NAME = "expenses.txt";

    private ArrayList<Expense> expenses = new ArrayList<>();
    private int nextId = 1;

    public ExpenseManager() {
        loadFromFile();
    }

    public void addExpense(String description, double amount, String category, String date, String employeeId) {
        Expense expense = new Expense(nextId, description, amount, category, date, employeeId);
        expenses.add(expense);
        nextId = nextId + 1;
        saveToFile();
        AuditLog.record(employeeId, "ADD_EXPENSE", "Added expense #" + expense.getId());
    }

    public boolean deleteExpense(int id, String employeeId) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == id) {
                expenses.remove(i);
                saveToFile();
                AuditLog.record(employeeId, "DELETE_EXPENSE", "Deleted expense #" + id);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Expense> getAllExpenses() {
        return expenses;
    }

    // monthPrefix should look like "2026-09"
    public ArrayList<Expense> filterByMonth(String monthPrefix) {
        ArrayList<Expense> results = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getDate().startsWith(monthPrefix)) {
                results.add(e);
            }
        }
        return results;
    }

    public ArrayList<Expense> filterByCategory(String category) {
        ArrayList<Expense> results = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category)) {
                results.add(e);
            }
        }
        return results;
    }

    private void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
            for (Expense e : expenses) {
                writer.write(e.toFileLine());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not save expenses: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                if (!line.isBlank()) {
                    Expense e = Expense.fromFileLine(line);
                    expenses.add(e);
                    if (e.getId() >= nextId) {
                        nextId = e.getId() + 1;
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not load expenses: " + e.getMessage());
        }
    }
}
