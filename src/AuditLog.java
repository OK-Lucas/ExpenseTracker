import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

// Appends a line to audit_log.txt every time an employee changes something.
public class AuditLog {
    private static final String FILE_NAME = "audit_log.txt";

    public static void record(String employeeId, String action, String details) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME, true);
            writer.write(LocalDateTime.now() + " | " + employeeId + " | " + action + " | " + details);
            writer.write(System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not write to audit log: " + e.getMessage());
        }
    }
}
