import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Server {

    private ServerSocket ss;
    private int playercount;
    private int playermax;

    private ArrayList<Integer> players = new ArrayList<>();

    public Server() {
        playercount = 0;

        try {
            ss = new ServerSocket(5000);
        } catch (IOException e) {
            System.out.println("IOException from Server constructor.");
        }
    }

    public void acceptPlayers() {
        try {
            Socket s = ss.accept();
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            playercount++;
            out.writeInt(playercount);
            players.add(playercount);
            // new variables here
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

        public void run() {
            try {
                // datain health
                // datain charges
                // datain action
                // datain target
            } catch (InterruptedException e) {
                System.out.println("InterruptedException from RFC run.");
            }
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
    }
}
