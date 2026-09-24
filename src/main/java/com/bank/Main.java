package main.java.com.bank;

import main.java.com.bank.model.*;
import main.java.com.bank.repository.AccountRepository;
import main.java.com.bank.repository.InMemoryAccountRepository;
import main.java.com.bank.service.AccountService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountRepository repository = new InMemoryAccountRepository();
        AccountService service = new AccountService(repository);
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("====== MENU ======");
            System.out.println("Choose an option: ");
            System.out.println("1. Create account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Account details");
            System.out.println("0. Exit");

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 0) {
                System.out.println("Logging out...");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.println("Enter account number: ");
                    String accountNumber = input.nextLine();
                    System.out.println("Enter account holder's name: ");
                    String name = input.nextLine();
                    System.out.println("Enter account holder's cpf: ");
                    String cpf = input.nextLine();
                    System.out.println("Enter account's type:\n ");
                    System.out.println("1. Checking Account");
                    System.out.println("2. Savings Account");
                    int type = input.nextInt();
                    input.nextLine();

                    Client client = new Client(name, cpf);

                    Account account;

                    if (type == 1) {
                        account = new CheckingAccount(accountNumber, client);
                    } else if (type == 2) {
                        account = new SavingsAccount(accountNumber, client);
                    } else {
                        throw new IllegalArgumentException("Invalid account type! (Type must be 1 or 2)");
                    }

                    try {
                        service.createAccount(account);
                        System.out.println("Account created successfully!");
                    } catch (Exception e) {
                        System.out.println("Error saving account: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Enter account number: ");
                    accountNumber = input.nextLine();
                    System.out.println("Enter deposit amount: ");
                    double depositAmount = input.nextDouble();
                    input.nextLine();
                    try {
                        service.deposit(depositAmount, accountNumber);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 3:
                    System.out.println("Enter account number: ");
                    accountNumber = input.nextLine();
                    System.out.println("Enter withdrawal amount: ");
                    double withdrawalAmount = input.nextDouble();
                    input.nextLine();
                    try {
                        service.withdraw(withdrawalAmount, accountNumber);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 4:
                    System.out.println("Enter account number: ");
                    Scanner searchAccountNumber = new Scanner(System.in);

                    Account foundAccount = service.getAccountDetails(searchAccountNumber.nextLine());

                    if (foundAccount != null) {
                        System.out.println("Account details: \n"
                                + "Account number: " + foundAccount.getAccountNumber()
                                + "\n Holder: " + foundAccount.getHolder().getName());
                        System.out.println("Balance: " + foundAccount.getBalance());
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;

            }
        }
        input.close();
    }
}
