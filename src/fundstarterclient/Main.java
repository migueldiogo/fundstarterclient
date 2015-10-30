package fundstarterclient;

import java.net.*;
import java.io.*;
import java.text.ParseException;


public class Main {
    public static void main(String args[]) throws IOException {
        //args[0] <- hostname of destination
        //args[1] <- hostname of destination (Backup Server)

        Socket s = null;
        int serverPort = 8200;
        GetPropertiesValues properties = new GetPropertiesValues();
        System.out.println(properties.getPrimaryServerIP() + " " + properties.getBackupServerIP());


        try {
            s = new Socket(args[0], serverPort);

            System.out.println("Welcome to FundStarter!");

            new Connection(s);

        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
        }
    }

}