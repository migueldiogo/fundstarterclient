package fundstarter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Question implements Serializable{
    private String projectName;
    private String Question;
    private ArrayList<String> answers;

    private static final long serialVersionUID = 1L;


    public Question(String projectName, String question, ArrayList<String> answers) {
        this.projectName = projectName;
        Question = question;
        this.answers = answers;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }
}

