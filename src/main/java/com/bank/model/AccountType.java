package main.java.com.bank.model;

public enum AccountType {
    CHECKING(1),
    SAVINGS(2);

    private final int id;

    AccountType(int id) {
        this.id =  id;
    }

    public int getId() {
        return id;
    }

}
