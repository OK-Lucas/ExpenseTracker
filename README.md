# ExpenseTracker

A simple command-line expense tracker built in Java. Started this mainly as a way to practice core Java stuff — OOP, collections, file handling, exceptions — while making something actually useful.

## Requirements

1. Record expenses (kept as objects in memory, saved to a text file)
2. Sort and filter data by month, type of expense, etc.
3. Save data even when the program is closed
4. Delete expenses
5. Employee "log on", with all changes tracked per employee
6. Store employee ID and PIN securely (hashed, never stored as plain text)
7. Possible integration into a larger business management system

## Project structure

```
src/
├── Main.java              # entry point, menu loop, reads user input
├── Expense.java           # one expense (id, description, amount, category, date, employee)
├── ExpenseManager.java    # list of expenses + add/delete/filter + save/load expenses.txt
├── Employee.java          # one employee (id + hashed PIN)
├── EmployeeManager.java   # register/login + save/load employees.txt + PIN hashing
└── AuditLog.java          # appends a line to audit_log.txt for every add/delete
```

No database, no external libraries — data is saved as plain text files (`expenses.txt`, `employees.txt`, `audit_log.txt`) that get created in the project folder the first time you run it.

## Running

From the `src` folder:

```
javac *.java
java Main
```

On first run, since there are no employees yet, it will prompt you to register one before logging in.

## Known limitations

- Descriptions/categories can't contain a `|` character (used as the separator when saving to file).
- Only one employee can be logged in at a time (no multi-user session support).
- PINs are hashed but not "salted" — fine for this project, but a real system would add a salt too.
