package Oasis;

import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String name;
    private String username;
    private String password;
    private String accountNo;

    public void register(Scanner sc) {
        System.out.println("\nEnter Your Name:");
        this.name = sc.nextLine().trim();
        while (this.name.isEmpty()) {
            System.out.println("\nName cannot be empty. Enter Your Name:");
            this.name = sc.nextLine().trim();
        }

        System.out.println("\nEnter Your Username:");
        this.username = sc.nextLine().trim();
        while (this.username.isEmpty()) {
            System.out.println("\nUsername cannot be empty. Enter Your Username:");
            this.username = sc.nextLine().trim();
        }

        System.out.println("\nEnter Your Password (must be exactly 6 characters):");
        this.password = sc.nextLine();
        while (this.password.length() != 6) {
            System.out.println("\nPassword must be exactly 6 characters. Try again:");
            this.password = sc.nextLine();
        }

        System.out.println("\nEnter Your Account Number (must be exactly 5 digits):");
        this.accountNo = sc.nextLine().trim();
        while (!this.accountNo.matches("\\d{5}")) {
            System.out.println("\nAccount number must be exactly 5 digits. Try again:");
            this.accountNo = sc.nextLine().trim();
        }

        System.out.println("\nRegistration Successful. A mandatory deposit of 5000 Rs will be added to your account.");
        System.out.println("Please log in to your Bank account.");
    }

    public boolean login(Scanner sc) {
        boolean isLogin = false;
        int attempts = 0;
        int maxAttempts = 3;
        while (!isLogin && attempts < maxAttempts) {
            System.out.println("\nEnter your username:");
            String inputUsername = sc.nextLine().trim();
            if (username.equals(inputUsername)) {
                while (!isLogin && attempts < maxAttempts) {
                    System.out.println("\nEnter your password:");
                    String inputPassword = sc.nextLine();
                    if (inputPassword.equals(password)) {
                        System.out.println("\nLogin Successful");
                        isLogin = true;
                    } else {
                        System.out.println("\nIncorrect Password!");
                        attempts++;
                        if (attempts < maxAttempts) {
                            System.out.println("Attempts remaining: " + (maxAttempts - attempts));
                        }
                    }
                }
            } else {
                System.out.println("\nUsername not found");
                attempts++;
                if (attempts < maxAttempts) {
                    System.out.println("Attempts remaining: " + (maxAttempts - attempts));
                }
            }
        }
        if (!isLogin) {
            System.out.println("\nToo many failed attempts. Exiting.");
            System.out.println("Thank you for using our ATM. Goodbye!");
            System.exit(0);
        }
        return isLogin;
    }

    public String getName() {
        return name;
    }
}

class Transaction {
    private String type;
    private float amount;
    private String recipient;

    public Transaction(String type, float amount, String recipient) {
        this.type = type;
        this.amount = amount;
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        if (recipient == null) {
            return amount + " Rs " + type + "\n";
        } else {
            return amount + " Rs transferred to " + recipient + "\n";
        }
    }
}

class BankAccount {
    private float balance = 0f;
    private static final float MINIMUM_BALANCE = 5000f;
    private static final float MAXIMUM_BALANCE = 500000f;
    private ArrayList<Transaction> transactions;

    public BankAccount() {
        transactions = new ArrayList<>();
        // Mandatory initial deposit
        balance = MINIMUM_BALANCE;
        transactions.add(new Transaction("Deposited", MINIMUM_BALANCE, null));
    }

    public void withdraw(Scanner sc) {
        System.out.println("\nEnter amount to withdraw:");
        try {
            float amount = sc.nextFloat();
            sc.nextLine(); // Clear buffer
            if (amount <= 0) {
                System.out.println("\nAmount must be positive.");
            } else if (balance - amount >= MINIMUM_BALANCE) {
                balance -= amount;
                System.out.println("\nWithdrawal successful");
                transactions.add(new Transaction("Withdrawn", amount, null));
            } else {
                System.out.println("\nInsufficient balance. You must maintain a minimum balance of 5000 Rs.");
            }
        } catch (Exception e) {
            System.out.println("\nError: Invalid input. Please enter a valid amount.");
            sc.nextLine();
        }
    }

    public void deposit(Scanner sc) {
        System.out.println("\nEnter amount to deposit:");
        try {
            float amount = sc.nextFloat();
            sc.nextLine();
            if (amount <= 0) {
                System.out.println("\nAmount must be positive.");
            } else if (balance + amount <= MAXIMUM_BALANCE) {
                balance += amount;
                System.out.println("\nDeposit successful");
                transactions.add(new Transaction("Deposited", amount, null));
            } else {
                System.out.println("\nDeposit would exceed 500000 Rs limit. Please upgrade to a Premium account at https://x.ai/premium.");
            }
        } catch (Exception e) {
            System.out.println("\nError: Invalid input. Please enter a valid amount.");
            sc.nextLine();
        }
    }

    public void transfer(Scanner sc) {
        System.out.println("\nEnter Recipient's Name:");
        String recipient = sc.nextLine().trim();
        if (recipient.isEmpty()) {
            System.out.println("\nRecipient's name cannot be empty.");
            return;
        }
        System.out.println("\nEnter Amount to transfer:");
        try {
            float amount = sc.nextFloat();
            sc.nextLine();
            if (amount <= 0) {
                System.out.println("\nAmount must be positive.");
            } else if (balance - amount >= MINIMUM_BALANCE) {
                if (amount <= MAXIMUM_BALANCE) {
                    balance -= amount;
                    System.out.println("\nSuccessfully transferred to " + recipient);
                    transactions.add(new Transaction("Transferred", amount, recipient));
                } else {
                    System.out.println("\nTransfer amount exceeds 500000 Rs limit. Please upgrade to a Premium account at https://x.ai/premium.");
                }
            } else {
                System.out.println("\nInsufficient Balance. You must maintain a minimum balance of 5000 Rs.");
            }
        } catch (Exception e) {
            System.out.println("\nError: Invalid input. Please enter a valid amount.");
            sc.nextLine();
        }
    }

    public void checkBalance() {
        System.out.println("\nCurrent Balance: " + balance + " Rs");
    }

    public void transactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions found (except mandatory initial deposit).");
        } else {
            System.out.println("\nTransaction History:");
            for (Transaction t : transactions) {
                System.out.print(t);
            }
        }
    }
}

class ATMMenu {
    private Scanner sc;

    public ATMMenu(Scanner sc) {
        this.sc = sc;
    }

    public int getIntegerInput(int limit) {
        int input = 0;
        boolean flag = false;
        while (!flag) {
            try {
                input = sc.nextInt();
                flag = true;
                if (input > limit || input < 1) {
                    System.out.println("Choose a number between 1 and " + limit + ":");
                    flag = false;
                }
            } catch (Exception e) {
                System.out.println("Enter only an integer value:");
                flag = false;
                sc.nextLine();
            }
        }
        sc.nextLine();
        return input;
    }

    public void showWelcomeMenu() {
        System.out.println("\n---------Welcome To ATM Interface----------");
        System.out.println("Choose one option:");
        System.out.println("1. Register");
        System.out.println("2. Quit");
    }

    public void showLoginMenu() {
        System.out.println("\nChoose one option:");
        System.out.println("1. Login");
        System.out.println("2. Quit");
    }

    public void showMainMenu(String name) {
        System.out.println("\n---------Welcome Back " + name + " ----------");
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Transfer");
        System.out.println("4. Check Balance");
        System.out.println("5. Transaction History");
        System.out.println("6. Quit");
    }
}

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ATMMenu menu = new ATMMenu(sc);

        menu.showWelcomeMenu();
        int choose = menu.getIntegerInput(2);

        if (choose == 1) {
            User user = new User();
            BankAccount account = new BankAccount();
            user.register(sc);

            while (true) {
                menu.showLoginMenu();
                int ch = menu.getIntegerInput(2);
                if (ch == 1) {
                    if (user.login(sc)) {
                        boolean isFinished = false;
                        while (!isFinished) {
                            menu.showMainMenu(user.getName());
                            int c = menu.getIntegerInput(6);
                            switch (c) {
                                case 1: account.withdraw(sc); break;
                                case 2: account.deposit(sc); break;
                                case 3: account.transfer(sc); break;
                                case 4: account.checkBalance(); break;
                                case 5: account.transactionHistory(); break;
                                case 6:
                                    isFinished = true;
                                    System.out.println("\nThank you for using our ATM. Goodbye!");
                                    break;
                            }
                        }
                    }
                } else {
                    System.out.println("\nThank you for using our ATM. Goodbye!");
                    System.exit(0);
                }
            }
        } else {
            System.out.println("\nThank you for using our ATM. Goodbye!");
            System.exit(0);
        }
        sc.close();
    }
}