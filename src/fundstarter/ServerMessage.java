package fundstarter;

import java.io.Serializable;

/**
 * Created by Miguel Prata Leal on 15/10/15.
 */
public class ServerMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    private String errorMessageBefore;
    private Object content;
    private boolean repeatAnswerToPrevious;

    public ServerMessage() {
        this.errorMessageBefore = "";
        this.repeatAnswerToPrevious = false;
    }

    public ServerMessage(ServerMessage serverMessageToCopy) {
        this.errorMessageBefore = serverMessageToCopy.getErrorMessageBefore();
        this.content = serverMessageToCopy.getContent();
        this.repeatAnswerToPrevious = serverMessageToCopy.isRepeatAnswerToPrevious();
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

    public boolean isRepeatAnswerToPrevious() {
        return repeatAnswerToPrevious;
    }

    public void setRepeatAnswerToPrevious(boolean repeatAnswerToPrevious) {
        this.repeatAnswerToPrevious = repeatAnswerToPrevious;
    }

    @Override
    public String toString() {
        return errorMessageBefore + "\n" + content;
    }

    // Return 1 in error
    public int verifyAnswer(ServerMessage message){
        if(!repeatAnswerToPrevious){
            System.out.println("Content: " + this.content);
            return 0;
        }
        else{
            System.out.println("Error: " + this.errorMessageBefore);
            return 1;
        }
    }

    public int decodeLogin(){
        if(this.repeatAnswerToPrevious == false){
            return 0;
        }
        else{
            return 1;
        }
    }


}



