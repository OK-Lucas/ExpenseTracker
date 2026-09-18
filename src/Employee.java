// One employee. We only ever store the PIN's hash, never the real PIN.
public class Employee {
    private String employeeId;
    private String pinHash;

    public Employee(String employeeId, String pinHash) {
        this.employeeId = employeeId;
        this.pinHash = pinHash;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getPinHash() {
        return pinHash;
    }

    public String toFileLine() {
        return employeeId + "|" + pinHash;
    }

    public static Employee fromFileLine(String line) {
        String[] parts = line.split("\\|");
        return new Employee(parts[0], parts[1]);
    }
}
