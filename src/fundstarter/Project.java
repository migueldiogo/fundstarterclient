package fundstarter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Project implements Serializable{
    private String creator;
    private String name;
    private String description;
    private String date;
    private double goal;
    private double totalAmountEarned;
    private ArrayList<Reward> rewards;
    private Question question;
    private ArrayList<Extra> extras;

    private static final long serialVersionUID = 1L;

    public Project() {}

    public Project(String creator, String name, String description, String date, double goal, ArrayList<Reward> rewards, Question question, ArrayList<Extra> extras) {
        this.creator = creator;
        this.name = name;
        this.description = description;
        this.date = date;
        this.goal = goal;
        this.rewards = rewards;
        this.question = question;
        this.extras = extras;
    }

    public double getPercentageOfProgress(){
        return (totalAmountEarned/goal)*100;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public ArrayList<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<Reward> rewards) {
        this.rewards = rewards;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public double getTotalAmountEarned() {
        return totalAmountEarned;
    }

    public void setTotalAmountEarned(double totalAmountEarned) {
        this.totalAmountEarned = totalAmountEarned;
    }

    public ArrayList<Extra> getExtras() {
        return extras;
    }

    public void setExtras(ArrayList<Extra> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "Project{" +
                "creator='" + creator + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", goal=" + goal +
                ", totalAmountEarned=" + totalAmountEarned +
                ", rewards=" + rewards +
                ", question=" + question +
                ", extras=" + extras +
                ", percentage=" + getPercentageOfProgress() +
                '}';
    }
}
