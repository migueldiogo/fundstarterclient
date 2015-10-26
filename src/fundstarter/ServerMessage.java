package fundstarter;

import java.io.Serializable;

/**
 * Created by Miguel Prata Leal on 15/10/15.
 */
public class ServerMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    private String errorMessageBefore;
    private Object content;
    private boolean errorHappened;

    public ServerMessage() {
        this.errorMessageBefore = "";
        this.errorHappened = false;
    }

    public ServerMessage(ServerMessage serverMessageToCopy) {
        this.errorMessageBefore = serverMessageToCopy.getErrorMessageBefore();
        this.content = serverMessageToCopy.getContent();
        this.errorHappened = serverMessageToCopy.isErrorHappened();
    }


    public String getErrorMessageBefore() {
        return errorMessageBefore;
    }

    public void setErrorMessageBefore(String errorMessageBefore) {
        this.errorMessageBefore = errorMessageBefore;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public boolean isErrorHappened() {
        return errorHappened;
    }

    public void setErrorHappened(boolean errorHappened) {
        this.errorHappened = errorHappened;
    }

    @Override
    public String toString() {
        return errorMessageBefore + "\n" + content;
    }

    // Return 1 in error
    public int verifyAnswer(ServerMessage message){
        if(!errorHappened){
            System.out.println("Content: " + this.content);
            return 0;
        }
        else{
            System.out.println("Error: " + this.errorMessageBefore);
            return 1;
        }
    }



}



