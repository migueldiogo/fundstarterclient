package fundstarter;

/**
 * Created by xavier on 26-10-2015.
 */
public class CancelProject {
    private String projectName;

    public CancelProject(String projectName) {
        this.projectName = projectName;
    }
    public CancelProject(){}

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
