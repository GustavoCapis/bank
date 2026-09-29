package main.java.com.bank;

import main.java.com.bank.model.*;
import main.java.com.bank.repository.AccountRepository;
import main.java.com.bank.repository.DbAccountRepository;
import main.java.com.bank.service.AccountService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountRepository repository = new DbAccountRepository();
        AccountService service = new AccountService(repository);
        Scanner input = new Scanner(System.in);

        /* TODO:
         *  Limpar o menu quando escolher opção
         *  Tratar erros de input inválidos
         *  Tratar erro de busca por conta inexistente
         *  Implementar DELETE
         *  Implementar extrato
         *  Implementar overDraft
         * */

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
                    String accountNumber = readValidAccountNumber(input, "Enter account number: ");
                    String name = readValidName(input, "Enter account holder's name: ");
                    String cpf = readValidCpf(input, "Enter account holder's cpf: ");
                    String accountType = readValidAccountType(input);

                    Client client = new Client(name, cpf);
                    Account account;

                    if (accountType.equals("1")) {
                        account = new CheckingAccount(accountNumber, client);
                    } else {
                        account = new SavingsAccount(accountNumber, client);
                    }
                    try {
                        service.createAccount(account);
                        System.out.println("Account created successfully!");
                    } catch (Exception e) {
                        System.out.println("Error saving account: " + e.getMessage());
                    }
                    break;

                case 2:
                    accountNumber = readValidAccountNumber(input, "Enter account number: ");
                    double depositAmount = readValidDouble(input, "Enter deposit amount: ");
                    try {
                        service.deposit(depositAmount, accountNumber);
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                case 3:
                    //TODO: mostrar saldo no console
                    System.out.println("Enter account number: ");
                    accountNumber = input.nextLine();
                    System.out.println("Enter withdrawal amount: ");
                    double withdrawalAmount = input.nextDouble();
                    input.nextLine();
                    try {
                        service.withdraw(withdrawalAmount, accountNumber);
                    } catch (Exception e) {
                        System.out.println("ERROR: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.println("Enter account number: ");
                    Scanner searchNum = new Scanner(System.in);

                    Account foundAccount = service.getAccountDetails(searchNum.nextLine());

                    if (foundAccount != null) {
                        System.out.println("Account details: \n" + "Account number: " + foundAccount.getAccountNumber());
                        System.out.println("Account holder: " + foundAccount.getHolder().getName());
                        System.out.println("Balance: " + foundAccount.getBalance());
                    } else {
                        System.out.println("Account not found!");
                    }
                    break;
            }

        }
        input.close();

    }

    private static String readValidAccountNumber(Scanner input, String prompt) {
        String line = "";
        String format = "\\d{4}-\\d";

        while (true) {
            System.out.print(prompt);
            line = input.nextLine().trim();

            if (line.isBlank()) {
                System.out.println("ERROR: Account number cannot be blank!");
            } else if (!line.matches(format)) {
                System.out.println("ERROR: Invalid format! Account number must follow the pattern 1234-5 (4 digits, hyphen, 1 digit).");
            } else {
                return line;
            }
        }
    }

    private static String readValidCpf(Scanner input, String prompt) {
        String format = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}";

        while (true) {
            String cpf = readValidString(input, prompt);

            if (cpf.matches(format)) {
                return cpf;
            }
            System.out.println("ERROR: Invalid format! CPF must follow the pattern 111.222.333-00");
        }
    }

    private static String readValidString(Scanner input, String prompt) {
        String line = "";
        while (line.isBlank()) {
            System.out.print(prompt);
            line = input.nextLine().trim();
            if (line.isBlank()) {
                System.out.println("ERROR: field cannot be empty! Please try again.");
            }
        }
        return line;
    }

    private static boolean isLettersOnly(String line) {
        return line.matches("^[a-zA-ZÀ-ÿ\\s]{2,}$");
    }

    private static String readValidName(Scanner input, String prompt) {
        while (true) {
            String name = readValidString(input, prompt);
            if (name.length() < 2) {
                System.out.println("ERROR: Name is too short! It must have at least 2 characters.");
            } else if (!isLettersOnly(name)) {
                System.out.println("ERROR: Name must contain only letters!");
            } else {
                return name;
            }
        }
    }

    private static String readValidAccountType(Scanner input) {
        while (true) {
            String prompt = """
                    Chose account's type:\s
                    1.Checking Account\s
                    2.Savings Account""";
            String type = readValidString(input, prompt);

            if (type.equals("1") || type.equals("2")) {
                return type;
            }
            System.out.println("ERROR: Invalid account type! (Type must be 1 or 2)");
        }
    }

    private static double readValidDouble(Scanner input, String prompt) {
        while (true) {
            String amount = readValidString(input, prompt);
            amount = amount.replaceAll(",", ".");
            double number;
            try {
                number = Double.parseDouble(amount);
                if (number <= 0) {
                    System.out.println("ERROR: Amount must be greater than 0.");
                } else {
                    return number;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid input! Must be a number greater than 0. Please try again.");
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

}
