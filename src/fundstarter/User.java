package fundstarter;

import java.io.Serializable;

/**
 * Created by sergiopires on 26/10/15.
 */
public class User implements Serializable{
    //TODO isto é preciso?
    private final double initialBalance=100;

    private static final long serialVersionUID = 1L;

    private int userId;
    private String username;
    private String password;
    private double balance;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int userId, String username, String password, double balance) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getInitialBalance() {
        return initialBalance;
    }
}
