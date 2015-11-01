package fundstarter;

import java.io.Serializable;

/**
 * Created by Miguel Prata Leal on 15/10/15.
 */
public class ServerMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    private Object content;
    private boolean errorHappened;

    public ServerMessage() {
        this.errorHappened = false;
    }

    public ServerMessage(ServerMessage serverMessageToCopy) {
        this.content = serverMessageToCopy.getContent();
        this.errorHappened = serverMessageToCopy.isErrorHappened();
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
        return content.toString();
    }





}



