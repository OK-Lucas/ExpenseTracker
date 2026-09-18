import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

// Handles employee registration and login, and saves/loads employees from a text file.
public class EmployeeManager {
    private static final String FILE_NAME = "employees.txt";

    private ArrayList<Employee> employees = new ArrayList<>();

    public EmployeeManager() {
        loadFromFile();
    }

    public boolean hasEmployees() {
        return employees.size() > 0;
    }

    public void registerEmployee(String employeeId, String pin) {
        String hash = hashPin(pin);
        employees.add(new Employee(employeeId, hash));
        saveToFile();
    }

    public boolean login(String employeeId, String pin) {
        String hash = hashPin(pin);
        for (Employee emp : employees) {
            if (emp.getEmployeeId().equals(employeeId) && emp.getPinHash().equals(hash)) {
                return true;
            }
        }
        return false;
    }

    // Scrambles the PIN into a fixed-length string so the real PIN is never written to disk.
    private String hashPin(String pin) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(pin.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 is not available", e);
        }
    }

    private void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
            for (Employee emp : employees) {
                writer.write(emp.toFileLine());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not save employees: " + e.getMessage());
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
                    employees.add(Employee.fromFileLine(line));
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not load employees: " + e.getMessage());
        }
    }
}
