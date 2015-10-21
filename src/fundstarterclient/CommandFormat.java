package fundstarterclient;

/**
 * Created by xavier on 19-10-2015.
 */
public class CommandFormat {
    protected String command;

    public CommandFormat(String command) {
        this.command = command;
    }
    public CommandFormat(){
        this.command = "";
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String appendString(String str){
        this.command += " ";
        this.command += str;
        return this.command;
    }

}
