package fundstarter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Question implements Serializable{
    private String projectName;
    private String Question;
    private ArrayList<Answer> answers = new ArrayList<>();

    private static final long serialVersionUID = 1L;


    public Question(String projectName, String question, ArrayList<Answer> answers) {
        this.projectName = projectName;
        Question = question;
        this.answers = answers;
    }

    public Question() {
        answers = new ArrayList<Answer>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public void addAnswer(String description, double shares) {
        answers.add(new Answer(description, shares));
    }

    @Override
    public String toString() {
        return "Question{" +
                "projectName='" + projectName + '\'' +
                ", Question='" + Question + '\'' +
                ", answers=" + answers +
                '}';
    }
}

