package main.java.com.bank.repository;

import main.java.com.bank.model.Account;
import main.java.com.bank.persistence.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbAccountRepository implements AccountRepository {
    private List<Account> accounts = new ArrayList<>();

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO accounts (account_number, holder_name, balance, account_type) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnection.connectDb();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, account.getAccountNumber());
            pstmt.setString(2, account.getHolder().getName());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setString(4, account.getClass().getSimpleName());

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
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }
}

