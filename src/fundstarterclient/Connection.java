package fundstarterclient;

import fundstarter.Command;
import fundstarter.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.ParseException;

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
            this.socket = new Socket();
            socket.connect(new InetSocketAddress(primaryServerIP, serverPort), 3000);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e){
            System.out.println("Server " + primaryServerIP.getHostAddress() + " not available: " + e.getMessage());
            try{
                this.socket = new Socket();
                socket.connect(new InetSocketAddress(secondaryServerIP, serverPort), 3000);

                this.outputStream = new ObjectOutputStream(socket.getOutputStream());
                this.inputStream = new ObjectInputStream(socket.getInputStream());
                this.secondaryServerIP = this.primaryServerIP;
                this.primaryServerIP = socket.getInetAddress();
            } catch(IOException e2){
                System.out.println("Server " + secondaryServerIP.getHostAddress() + " not available: " + e.getMessage());
                System.exit(1);
            }
        }

        startCLI();


    }

    public void startCLI() {
        ShowMenus menus = new ShowMenus(this);
        menus.start();
    }

    public ServerMessage handleServerFailOver(Command commandInOutbox, String usernameLoggedIn) {
        // o cliente tenta uma última vez ligar-se ao servidor primário
        ServerMessage outputMessageToClient = null;
        System.out.println("Server is not available. Command in outbox: " + commandInOutbox.getCommand());
        try {
            System.out.println("Try to reestablish connection with primary server...");
            socket = new Socket(socket.getInetAddress(), socket.getPort());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            if (!usernameLoggedIn.equals(""))
                reLogin(usernameLoggedIn);
            outputMessageToClient = reSendCommand(commandInOutbox);
        } catch (IOException e) {
            try {
                System.out.println("Try to reestablish connection with backup server...");

                socket = new Socket(secondaryServerIP, serverPort);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                inputStream = new ObjectInputStream(socket.getInputStream());
                secondaryServerIP = primaryServerIP;
                primaryServerIP = socket.getInetAddress();
                if (!usernameLoggedIn.equals(""))
                    reLogin(usernameLoggedIn);
                outputMessageToClient = reSendCommand(commandInOutbox);


            } catch (IOException e2) {
                System.out.println("No server available... i'm out.");

                System.exit(1);
            }
        }

        return outputMessageToClient;

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

    public ServerMessage reSendCommand(Command commandInOutbox) throws IOException{
        ServerMessage output = null;
        outputStream.writeObject(commandInOutbox);
        try {
            output = (ServerMessage)inputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found: " + e.getMessage());
        }
        return output;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
