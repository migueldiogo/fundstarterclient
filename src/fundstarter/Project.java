package fundstarter;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Project implements Serializable{
    private static final long serialVersionUID = 1L;

    private int projectId;
    private String name;
    private String description;
    private Date expirationDate;
    private double firstGoalValue;
    private double totalAmountEarned;
    private String question;
    private Long tumblrPostId;

    public Project() {
    }

    public Project(String name, String description, Date expirationDate, double firstGoalValue) {
        this.name = name;
        this.description = description;
        this.expirationDate = expirationDate;
        this.firstGoalValue = firstGoalValue;
    }

    public Project(int projectId, String name, String description, Date expirationDate, double firstGoalValue, double totalAmountEarned) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.expirationDate = expirationDate;
        this.firstGoalValue = firstGoalValue;
        this.totalAmountEarned = totalAmountEarned;
    }


    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getFirstGoalValue() {
        return firstGoalValue;
    }

    public void setFirstGoalValue(double firstGoalValue) {
        this.firstGoalValue = firstGoalValue;
    }

    public double getTotalAmountEarned() {
        return totalAmountEarned;
    }

    public void setTotalAmountEarned(double totalAmountEarned) {
        this.totalAmountEarned = totalAmountEarned;
    }

    public double getPercentageOfProgress(){
        return (totalAmountEarned/ firstGoalValue)*100;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getTumblrPostId() {
        return tumblrPostId;
    }

    public void setTumblrPostId(Long tumblrPostId) {
        this.tumblrPostId = tumblrPostId;
    }

    @Override
    public String toString() {
        return "Project{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", firstGoalValue=" + firstGoalValue +
                ", totalAmountEarned=" + totalAmountEarned +
                '}';
    }
}
