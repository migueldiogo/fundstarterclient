package fundstarter;

import java.io.Serializable;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Pledge implements Serializable{
    private static final long serialVersionUID = 1L;

    private int pledgeId;
    private String projectName;
    private int pledgeUserId;
    private int projectId;
    private double amount;
    private int decision;
    private String decisionDescription;


    public Pledge() {
    }

    public Pledge(int pledgeUserId, int projectId, double amount, int decision) {
        this.pledgeUserId = pledgeUserId;
        this.projectId = projectId;
        this.amount = amount;
        this.decision = decision;
    }

    public Pledge(int pledgeUserId, int projectId, double amount, String decisionDescription) {
        this.pledgeUserId = pledgeUserId;
        this.projectId = projectId;
        this.amount = amount;
        this.decisionDescription = decisionDescription;
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
        return pledgeUserId;
    }

    public void setPledgerUserId(int pledgerUsreId) {
        this.pledgeUserId = pledgerUsreId;
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

    public String getDecisionDescription() {
        return decisionDescription;
    }

    public void setDecisionDescription(String decisionDescription) {
        this.decisionDescription = decisionDescription;
    }

    @Override
    public String toString() {
        return "Pledge{" +
                "amount=" + amount +
                ", pledgeId=" + pledgeId +
                ", pledgeUserId=" + pledgeUserId +
                ", projectId=" + projectId +
                ", decision='" + decision + '\'' +
                '}';
    }
}
