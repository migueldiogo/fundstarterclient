package fundstarter;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Admin {
    private String projectName;
    private String username;

    public Admin(String projectName, String username) {
        this.projectName = projectName;
        this.username = username;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
