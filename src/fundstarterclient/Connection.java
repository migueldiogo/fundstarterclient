package fundstarterclient;

import fundstarter.Command;
import fundstarter.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Created by xavier on 26-10-2015.
 */
public class Connection {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private InetAddress primaryServerIP;
    private InetAddress secondaryServerIP;
    private int serverPort;

    public Connection(Socket socket) throws IOException, ParseException {
        try{
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e){
            System.out.println("Connection: " + e.getMessage());
        }


    }

    public Connection(InetAddress primaryServerIP, InetAddress secondaryServerIP, int serverPort) throws IOException, ParseException {
        try{
            this.primaryServerIP = primaryServerIP;
            this.secondaryServerIP = secondaryServerIP;
            this.serverPort = serverPort;
            this.socket = new Socket(primaryServerIP, serverPort);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e){
            System.out.println("Connection: " + e.getMessage());
        }


    }

    public void startCLI() {
        ShowMenus menus = new ShowMenus(inputStream, outputStream, this);

        try {
            menus.start();
        }
        catch (IOException e) {
            handleServerFailOver();
        }
        catch (ParseException e) {
            System.out.println("Parse Exception: " + e.getMessage());
        }

    }

    public Socket handleServerFailOver(Command commandInOutbox, String usernameLoggedIn) {
        // o cliente tenta uma última vez ligar-se ao servidor primário
        try {

            socket = new Socket(socket.getInetAddress(), socket.getPort());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            if (!usernameLoggedIn.equals(""))
                reLogin(usernameLoggedIn);
        } catch (IOException e) {
            try {
                socket = new Socket(secondaryServerIP, serverPort);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                if (!usernameLoggedIn.equals(""))
                    reLogin(usernameLoggedIn);
            } catch (IOException e2) {
                System.exit(1);
            }
        }

        return socket;

    }

    public void reLogin(String usernameLoggedIn) throws IOException{
        Command command = new Command();

        command.setCommand("loginFailOver");
        command.addArgument(usernameLoggedIn);

        outputStream.writeObject(command);
        try {
            inputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found: " + e.getMessage());;
        }
    }

    public void reSendCommand(Command commandInOutbox) throws IOException{

        outputStream.writeObject(commandInOutbox);
        try {
            System.out.println(inputStream.readObject().toString());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found: " + e.getMessage());;
        }
    }

}
