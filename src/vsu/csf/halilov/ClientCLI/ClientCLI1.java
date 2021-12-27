package vsu.csf.halilov.ClientCLI;

import vsu.csf.halilov.CLI.ConsoleInterface;
import vsu.csf.halilov.Game.Chess;
import vsu.csf.halilov.Pieces.Square;
import vsu.csf.halilov.enums.GameState;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;

public class ClientCLI1 {

    public static void main(String[] args) {


        final String serverHost = "localhost";

        Socket socketOfClient = null;
        BufferedWriter os = null;
        BufferedReader is = null;

        try {


            // Send a request to connect to the server is listening
            // on machine 'localhost' port 7777.
            socketOfClient = new Socket(serverHost, 7777);

            // Create output stream at the client (to send data to the server)
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));


            // Input stream at Client (Receive data from the server).
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverHost);
            return;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverHost);
            return;
        }

        try {
            System.out.println("!!");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String message = scanner.nextLine();
                os.write(message);
                os.newLine();
                os.flush();
                printFromServer(is);
                if (message.equals("stop")){
                    break;
                }
            }





            os.close();
            is.close();
            socketOfClient.close();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }

    public static void printFromServer(BufferedReader is) throws IOException {
        String s;
        if ((s = is.readLine()) != null)
            System.out.println("Server: " + s);
        if (s.indexOf("OK") != -1) {
            return;
        }
    }


}
