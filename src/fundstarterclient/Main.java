package fundstarterclient;

import java.net.*;
import java.io.*;
import java.text.ParseException;


public class Main {
    public static void main(String args[]) throws IOException {
        //args[0] <- hostname of destination
        //args[1] <- hostname of destination (Backup Server)

        //Socket s = null;
        int serverPort = 8200;
        GetPropertiesValues properties = new GetPropertiesValues();
        InetAddress primaryServerAddress = InetAddress.getByName(properties.getPrimaryServerIP());
        InetAddress secondaryServerAddress = InetAddress.getByName(properties.getBackupServerIP());
        System.out.println(properties.getPrimaryServerIP() + " " + properties.getBackupServerIP());
        System.out.println("Welcome to FundStarter!");

        try {
            new Connection(primaryServerAddress, secondaryServerAddress, serverPort);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}