package ui;
import java.util.Scanner;


public class ConsoleMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                // placeholder values for now
                case 1: System.out.println("1. Add Expense");
                case 2: System.out.println("2. View Expense");
                case 3: System.out.println("3. Delete Expense");
                case 0: running = false; // leave loop
                default: System.out.println("Invalid choice. Please try again.");
            }

        } // end loop

        System.out.println("Program Ending...");

    } // end start

} // end class