package main.java.com.bank.repository;

import main.java.com.bank.model.Account;
import main.java.com.bank.model.CheckingAccount;
import main.java.com.bank.model.Client;
import main.java.com.bank.model.SavingsAccount;
import main.java.com.bank.persistence.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbAccountRepository implements AccountRepository {

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO accounts (holder_cpf, account_number, holder_name, balance, account_type) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnection.connectDb();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, account.getHolder().getCpf());
            pstmt.setString(2, account.getAccountNumber());
            pstmt.setString(3, account.getHolder().getName());
            pstmt.setDouble(4, account.getBalance());
            pstmt.setString(5, account.getClass().getSimpleName());

            pstmt.executeUpdate();
            System.out.println("Account save success.");
        } catch (SQLException e) {
            System.out.println("ERROR:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Account findByNumber(String accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Account account = null;

        try {
            conn = DatabaseConnection.connectDb();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, accountNumber);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String cpf = rs.getString("holder_cpf");
                String accNumber = rs.getString("account_number");
                String holderName = rs.getString("holder_name");
                double balance = rs.getDouble("balance");
                String accountType = rs.getString("account_type");

                Client client = new Client(holderName, cpf);

                switch (accountType) {
                    case "CheckingAccount":
                        account = new CheckingAccount(accountNumber, client);
                        break;

                    case "SavingsAccount":
                        account = new SavingsAccount(accountNumber, client);
                        break;

                    default:
                        System.out.println("Account type not found." + accountType);
                }

                if (account != null) {
                    account.setAccountNumber(accNumber);
                    account.setBalance(balance);
                }

            }
        } catch (SQLException e) {
            System.out.println("ERROR:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        String sql = "SELECT * FROM accounts";
        List<Account> accountList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.connectDb();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String cpf = rs.getString("holder_cpf");
                String accNumber = rs.getString("account_number");
                String holderName = rs.getString("holder_name");
                double balance = rs.getDouble("balance");
                String accountType = rs.getString("account_type");

                Client client = new Client(holderName, cpf);
                Account account = null;

                switch (accountType) {
                    case "CheckingAccount":
                        account = new CheckingAccount(accNumber, client);
                        break;

                    case "SavingsAccount":
                        account = new SavingsAccount(accNumber, client);
                        break;

                    default:
                        System.out.println("Account type not found: " + accountType);
                }
                if (account != null) {
                    account.setBalance(balance);
                    accountList.add(account);
                }
            }

        } catch (SQLException e) {
            System.out.println("ERROR ao listar contas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return accountList;
    }

    @Override
    public void updateBalance(Account account) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnection.connectDb();
            pstmt = conn.prepareStatement(sql);

            pstmt.setDouble(1, account.getBalance());
            pstmt.setString(2, account.getAccountNumber());

            pstmt.executeUpdate();
            System.out.println("Balance updated successfully.");

        } catch (SQLException e){
            System.out.println("ERROR:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}