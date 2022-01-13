package vsu.csf.halilov.Server;

import vsu.csf.halilov.CLI.ConsoleInterface;
import vsu.csf.halilov.Game.Chess;
import vsu.csf.halilov.Pieces.Square;
import vsu.csf.halilov.enums.GameState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    static Chess chess = new Chess();


    public static void main(String args[]) throws IOException {

        ServerSocket listener = null;
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;

        // Try to open a server socket on port 7777
        // Note that we can't choose a port less than 1023 if we are not
        // privileged users (root)

        try {
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            while (true) {
                // Accept client connection request
                // Get new Socket at Server.

                Socket socketOfServer = listener.accept();
                new ServiceThread(socketOfServer, clientNumber++).start();
            }
        } finally {
            listener.close();
        }

    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static class ServiceThread extends Thread {

        private int clientNumber;
        private Socket socketOfServer;

        public ServiceThread(Socket socketOfServer, int clientNumber) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;

            // Log
            log("New connection with client# " + this.clientNumber + " at " + socketOfServer);
        }

        @Override
        public void run() {

            try {

                // Open input and output streams
                BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
                Chess chess = new Chess();
                while (true) {


                    String line = is.readLine();
                    System.out.println("Client printed " + line);

                    Square startingSquareInput = ConsoleInterface.getStartingSquareFromString(line);
                    Square targetSquareInput = ConsoleInterface.getTargetSquareFromString(line);

                    //getting actual squares out of coords
                    Square startingSquare = chess.getBoard(startingSquareInput.getRow(), startingSquareInput.getCol());
                    Square targetSquare = chess.getBoard(targetSquareInput.getRow(), targetSquareInput.getCol());

                    if (!chess.combinedMoveChecking(startingSquare, targetSquare)) {
                        System.out.println("Impossible move, try again: ");
                        os.write("Impossible move, try again: ");
                        os.newLine();
                        os.flush();
                    } else {

                    chess.move(startingSquare, targetSquare);
                    os.write(chess.randomResponse());
                    os.newLine();
                    os.flush();

                    chess.printBoard();
                    }
                }

            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }


        }
    }
}
