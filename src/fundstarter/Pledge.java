package fundstarter;

import java.io.Serializable;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Pledge implements Serializable{
    private static final long serialVersionUID = 1L;

    private int pledgeId;
    private String projectName;
    private int pledgerUsreId;
    private int projectId;
    private double amount;
    private int decision;

    public Pledge() {
    }

    public Pledge(int pledgerUsreId, int projectId, double amount, int decision) {
        this.pledgerUsreId = pledgerUsreId;
        this.projectId = projectId;
        this.amount = amount;
        this.decision = decision;
    }

    public int getPledgeId() {
        return pledgeId;
    }

    public void setPledgeId(int pledgeId) {
        this.pledgeId = pledgeId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getPledgerUserId() {
        return pledgerUsreId;
    }

    public void setPledgerUserId(int pledgerUsreId) {
        this.pledgerUsreId = pledgerUsreId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getDecision() {
        return decision;
    }

    public void setDecision(int decision) {
        this.decision = decision;
    }

    @Override
    public String toString() {
        return "Pledge{" +
                "amount=" + amount +
                ", pledgeId=" + pledgeId +
                ", pledgerUsreId=" + pledgerUsreId +
                ", projectId=" + projectId +
                ", decision='" + decision + '\'' +
                '}';
    }
}
