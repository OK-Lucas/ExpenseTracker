// One expense entry.
public class Expense {
    private int id;
    private String description;
    private double amount;
    private String category;
    private String date;
    private String employeeId;

    public Expense(int id, String description, double amount, String category, String date, String employeeId) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String toString() {
        return "#" + id + " | " + date + " | $" + amount + " | " + category + " | " + description + " | added by " + employeeId;
    }

    // Turns this expense into one line of text so it can be saved to a file.
    public String toFileLine() {
        return id + "|" + description + "|" + amount + "|" + category + "|" + date + "|" + employeeId;
    }

    // Rebuilds an Expense from one line of text read from a file.
    public static Expense fromFileLine(String line) {
        String[] parts = line.split("\\|");
        int id = Integer.parseInt(parts[0]);
        String description = parts[1];
        double amount = Double.parseDouble(parts[2]);
        String category = parts[3];
        String date = parts[4];
        String employeeId = parts[5];
        return new Expense(id, description, amount, category, date, employeeId);
    }
}
