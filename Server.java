import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server {

    private ServerSocket ss;
    private int playercount;
    private int playermax;

    private ArrayList<LinkedList> players = new ArrayList<>();


    public Server() {
        playercount = 0;

        try {
            ss = new ServerSocket(5000);
        } catch (IOException e) {
            System.out.println("IOException from Server constructor.");
        }
    }

    /**
     * 
     */
    public void acceptPlayers() {
        try {
            Socket s = ss.accept();
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            playercount++;
            out.writeInt(playercount);

        } catch (IOException e) {
            System.out.println("IOException from acceptPlayers().");
        }
    }

    private class ReadFromClient implements Runnable {

        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int id, DataInputStream in) {
            playerID = id;
            dataIn = in;
        }

        /**
         * Runs actual game after both players join
         */
        public void run() {
            try {
                for(Integer i : players)
                // datain health
                // datain charges
                // datain action
                // datain target
            } catch (InterruptedException e) {
                System.out.println("InterruptedException from RFC run.");
            }
        }

        public void getPlayerDetails() {
            // gets the player details from each client through their respective input
            // streams
            // and puts the details in a linked list to access for later
        }

    }

    private class WriteToClient implements Runnable {

        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int id, DataOutputStream out) {
            playerID = id;
            dataOut = out;
        }

        public void run() {
            try {
                for (Integer i : players) {

                }
            } catch (InterruptedException e) {
                System.out.println("InterruptedException from WTC run.");
            }
        }

        public void writePlayerDetails() {
            // outs the details of the players to each other, not sending a player's details
            // to themself
        }
    }
}
