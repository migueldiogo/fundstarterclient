package fundstarter;

import java.io.Serializable;

/**
 * Created by sergiopires on 26/10/15.
 */
public class AttributedGifts implements Serializable{
    private String sendFrom;
    private String projectName;
    private String giftName;
    private String sendTo;

    private static final long serialVersionUID = 1L;


    public AttributedGifts(String username, String projectName, String giftName, String sender) {
        this.sendFrom = username;
        this.projectName = projectName;
        this.giftName = giftName;
        this.sendTo = sender;
    }
    public AttributedGifts(){}


    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
