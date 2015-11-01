package fundstarter;

import java.io.Serializable;

/**
 * Created by Miguel Prata Leal on 01/11/15.
 */
public class Answer implements Serializable{
    private static final long serialVersionUID = 1L;

    private String description;
    private double votes;

    public Answer(String description, double votes) {
        this.description = description;
        this.votes = votes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getVotes() {
        return votes;
    }

    public void setVotes(double votes) {
        this.votes = votes;
    }
}
