package fundstarterclient;


import fundstarter.ServerMessage;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        //args[0] <- hostname of destination


        Socket s = null;
        int serversocket = 8200;
        ShowMenus menu = new ShowMenus();
        String command = "";
        ServerMessage message = new ServerMessage();

        try {
            s = new Socket(args[0], serversocket);

            System.out.println("Welcome to FundStarter!");

            Connection c = new Connection(s);

            menu.initiateMenuDrivenWithClient(in, out);

            System.out.println(command);


            // 3o passo
            while (true) {
                //ServerMessage message = (ServerMessage) in.readObject();
                System.out.println(message.toString());


                out.writeUTF(command);

            }

        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } catch (ClassNotFoundException e) {
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