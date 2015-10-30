
package fundstarter;

import java.io.Serializable;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Extra implements Serializable{

    private double goalMin;
    private String projectName;
    private String description;

    private static final long serialVersionUID = 1L;


    public Extra(double goalMin, String projectName, String description) {
        this.goalMin = goalMin;
        this.projectName = projectName;
        this.description = description;
    }
    public Extra(){}

    public double getGoalMin() {
        return goalMin;
    }

    public void setGoalMin(double goalMin) {
        this.goalMin = goalMin;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Extra{" +
                "goalMin=" + goalMin +
                ", projectName='" + projectName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}