package fundstarterclient;

import java.net.*;
import java.io.*;
import java.text.ParseException;
import java.util.Properties;


public class Main {
    public static void main(String args[]) throws IOException {

        int serverPort = 0;
        InetAddress primaryServerAddress = InetAddress.getByName("localhost");
        InetAddress secondaryServerAddress = InetAddress.getByName("localhost");

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("resources/config.properties"));
            serverPort = Integer.parseInt(properties.getProperty("ServerPort"));
            primaryServerAddress = InetAddress.getByName(properties.getProperty("PrimaryServerIP"));
            secondaryServerAddress = InetAddress.getByName(properties.getProperty("SecondaryServerIP"));
        } catch (IOException e) {
            if (args.length == 0) {
                System.out.println("No properties file found. Please rerun the program with the following arguments: \n" +
                        "\t-primary server IP;\n" + "\t-secondary server IP;\n" + "\t-server port.");
                System.exit(1);
            }
            else {
                serverPort = Integer.parseInt(args[0]);
                primaryServerAddress = InetAddress.getByName(args[1]);
                secondaryServerAddress = InetAddress.getByName(args[2]);
            }

        }

        System.out.println("FUNDSTARTER (CLI)");
        System.out.println("by Miguel Prata Leal, Sergio Pires, Xavier Silva");
        System.out.println("@Sistemas Distribuidos, Universidade de Coimbra -- Coimbra 2015");


        System.out.println("Primary Server: " + properties.getProperty("PrimaryServerIP") +
                            ", Secundary Server: " + properties.getProperty("SecondaryServerIP") +
                            ", Server(s) port: " + serverPort);




        try {
            new Connection(primaryServerAddress, secondaryServerAddress, serverPort);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}