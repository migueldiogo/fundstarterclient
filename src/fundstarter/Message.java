package fundstarter;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 1L;

    private int messageId;
    private Timestamp dateTime;
    private String text;
    private int projectId;
    private int pledgerUserId;
    private boolean messageFromProject;
    private String projectName;
    private String pledgerUsername;

    public Message() {
    }

    public Message(int messageId, Timestamp dateTime, String text, int projectId, int pledgerUserId) {
        this.messageId = messageId;
        this.dateTime = dateTime;
        this.text = text;
        this.projectId = projectId;
        this.pledgerUserId = pledgerUserId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getPledgerUserId() {
        return pledgerUserId;
    }

    public void setPledgerUserId(int pledgerUserId) {
        this.pledgerUserId = pledgerUserId;
    }

    public boolean isMessageFromProject() {
        return messageFromProject;
    }

    public void setMessageFromProject(boolean messageFromProject) {
        this.messageFromProject = messageFromProject;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPledgerUsername() {
        return pledgerUsername;
    }

    public void setPledgerUsername(String pledgerUsername) {
        this.pledgerUsername = pledgerUsername;
    }

    @Override
    public String toString() {
        return "Message{" +
                "dateTime=" + dateTime +
                ", messageId=" + messageId +
                ", text='" + text + '\'' +
                ", projectId=" + projectId +
                ", pledgerUserId=" + pledgerUserId +
                '}';
    }
}
