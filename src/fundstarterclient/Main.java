package fundstarterclient;

import java.net.*;
import java.io.*;
import java.text.ParseException;

public class Main {
    public static void main(String args[]) {
        //args[0] <- hostname of destination
        //args[1] <- hostname of destination (Backup Server)

        Socket s = null;
        int serversocket = 8200;

        try {
            s = new Socket(args[0], serversocket);

            System.out.println("Welcome to FundStarter!");

            Connection connection = new Connection(s);

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