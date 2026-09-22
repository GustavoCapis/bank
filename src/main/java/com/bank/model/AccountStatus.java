package main.java.com.bank.model;

public enum AccountStatus {
    BLOCKED("BLOCKED"),
    ACTIVE("ACTIVE"),
    CLOSED("CLOSED");

    private final String status;

    AccountStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
