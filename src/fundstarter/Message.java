package fundstarter;

import java.io.Serializable;

/**
 * Created by sergiopires on 26/10/15.
 */
public class Message implements Serializable{
    private String sendTo;
    private String sendFrom;
    private String text;
    private String data;
    private String projectAssociate;


    private static final long serialVersionUID = 1L;


    public Message(String sendTo, String sendFrom, String text, String data) {
        this.sendTo = sendTo;
        this.sendFrom = sendFrom;
        this.text = text;
        this.data = data;
    }

    public Message() {
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProjectAssociate() {
        return projectAssociate;
    }

    public void setProjectAssociate(String projectAssociate) {
        this.projectAssociate = projectAssociate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "data='" + data + '\'' +
                ", sendTo='" + sendTo + '\'' +
                ", sendFrom='" + sendFrom + '\'' +
                ", text='" + text + '\'' +
                ", projectAssociate='" + projectAssociate + '\'' +
                '}';
    }
}
