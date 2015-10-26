package fundstarter;

import java.io.Serializable;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Pledge implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String projectName;
    private double amount;
    private String answer;

    public Pledge(String username, String projectName, double amount, String answer) {
        this.username = username;
        this.projectName = projectName;
        this.amount = amount;
        this.answer = answer;
    }
    public Pledge(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
