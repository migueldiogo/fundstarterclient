package fundstarter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Project implements Serializable{
    private String name;
    private String description;
    private String date;
    private double goal;
    private ArrayList<Reward> rewards;
    private Question question;
    private static final long serialVersionUID = 1L;


    public Project(String name, String description, String date, Question question, double goal, ArrayList<Reward> rewards) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.question = question;
        this.goal = goal;
        this.rewards = rewards;
    }

    public Project(){
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

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", goal=" + goal +
                ", rewards=" + rewards +
                ", question=" + question +
                '}';
    }
}
