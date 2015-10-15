package fundstarterclient;


import fundstarter.ServerMessage;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        // args[0] <- hostname of destination


        Socket s = null;
        int serversocket = 8100;
        try {
            s = new Socket("localhost", serversocket);

            System.out.println("Welcome to FundStarter!");

            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            DataOutputStream out = new DataOutputStream(s.getOutputStream());



            // 3o passo
            while (true) {
                ServerMessage message = (ServerMessage) in.readObject();
                System.out.println(message.toString());
                Scanner scan = new Scanner(System.in);
                out.writeUTF(scan.nextLine());

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