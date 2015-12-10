package fundstarter;

import java.io.Serializable;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Reward implements Serializable{
    private static final long serialVersionUID = 1L;

    private int userId;
    private double rewardId;
    private double minAmount;
    private String description;
    private boolean done;
    private int projectId;
    private String projectName;

    public Reward() {
    }

    public Reward(int userId, double minAmount, String description) {
        this.userId = userId;
        this.minAmount = minAmount;
        this.description = description;
    }

    public double getRewardId() {
        return rewardId;
    }

    public void setRewardId(double rewardId) {
        this.rewardId = rewardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "description='" + description + '\'' +
                ", userId=" + userId +
                ", minAmount=" + minAmount +
                ", done=" + done +
                '}';
    }
}
