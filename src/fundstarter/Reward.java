package fundstarter;

import java.io.Serializable;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Reward implements Serializable{
    private String projectName;
    private double pledgeMin;
    private String giftName;
    private static final long serialVersionUID = 1L;



    public Reward(String projectName, double pledgeMin, String giftName) {
        this.projectName = projectName;
        this.pledgeMin = pledgeMin;
        this.giftName = giftName;
    }
    public Reward(){}


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getPledgeMin() {
        return pledgeMin;
    }

    public void setPledgeMin(double pledgeMin) {
        this.pledgeMin = pledgeMin;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }
}
