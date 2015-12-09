package fundstarter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Message implements Serializable{
    private static final long serialVersionUID = 1L;

    private int messageId;
    private Date date;
    private String text;
    private int projectId;
    private int pledgerUserId;

    public Message() {
    }

    public Message(int messageId, Date date, String text, int projectId, int pledgerUserId) {
        this.messageId = messageId;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "Message{" +
                "date=" + date +
                ", messageId=" + messageId +
                ", text='" + text + '\'' +
                ", projectId=" + projectId +
                ", pledgerUserId=" + pledgerUserId +
                '}';
    }
}
