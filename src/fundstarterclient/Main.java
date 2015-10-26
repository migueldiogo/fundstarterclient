package fundstarterclient;


import fundstarter.ServerMessage;

import java.net.*;
import java.io.*;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        //args[0] <- hostname of destination


        Socket s = null;
        int serversocket = 8200;
        String command = "";
        ServerMessage message = new ServerMessage();

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