package fundstarterclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by xavier on 26-10-2015.
 */
public class Connection {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Connection(Socket socket) {
        try{
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e){
            System.out.println("Connection: " + e.getMessage());
        }

        ShowMenus menus = new ShowMenus(inputStream, outputStream);
        menus.start();

        //System.out.println(command);

    }

}
