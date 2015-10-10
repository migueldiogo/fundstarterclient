package fundstarterclient;

import java.net.*;
import java.io.*;

public class Main {
    public static void main(String args[]) {
        // args[0] <- hostname of destination
        if (args.length == 0) {
            System.out.println("java TCPClient hostname");
            System.exit(0);
        }

        Socket s = null;
        int serversocket = 7000;
        try {
            // 1o passo
            s = new Socket(args[0], serversocket);

            System.out.println("Welcome to FundStarter!");
            // 2o passo
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            // Comandos
            String comando = "";

            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(input);

            // 3o passo
            while (true) {
                // Ler comandos do teclado
                try {
                    // ... Lista de comandos a adiconar ... //
                    System.out.print(">>> ");
                    comando = reader.readLine();
                } catch (Exception e) {}


                // Enviar comando atravès do Socket
                out.writeUTF(comando);

                // Ler a partir do Socket
                String data = in.readUTF();
                System.out.println("Recebeu: " + data);
            }

        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
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