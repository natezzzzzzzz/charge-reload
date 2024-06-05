import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.*;

public class Server_test{

    private ServerSocket ss;
    private int playercount;
    private final int playermax = 2;
    private boolean hostStarts;

    private ArrayList<LinkedList> players = new ArrayList<>();
    //Data for players and for each player ↓↓
    // player at index 0 --> player object with specified name and id
    // player at index 1 --> socket
    // player at index 2 --> rfc, read runnable
    // player at index 3 --> wfc, write runnable

    public Server_test() {
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
            while(hostStarts || playercount < playermax){
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                playercount++;
                out.writeInt(playercount);
                System.out.println("Player " + playercount + " has connected!");

                ReadFromClient rfc = new ReadFromClient(playercount, in);
                ReadFromClient wfc = new ReadFromClient(playercount, in);
                Scanner input = new Scanner(System.in);

                System.out.println("Enter name:");
                String name = input.nextLine();
                LinkedList<Object> player_data = new LinkedList<Object>();
                player_data.add(new Player(name,playercount));
                player_data.add(s);
                player_data.add(rfc);
                player_data.add(wfc);
                players.add(player_data);
                }
            //send start msg for each player
            //wait ready player

            System.out.println("No longer accepting connections.");
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
                for(LinkedList player : players){
                    ((Player) player.get(0)).setHealth(dataIn.readInt());
                    ((Player) player.get(0)).setCharges(dataIn.readInt());
                    ((Player) player.get(0)).setTarget((Player)players.get(dataIn.readInt()-1).get(0));
                    ((Player) player.get(0)).setBlock(dataIn.readInt());
                    ((Player) player.get(0)).setCharges(dataIn.readInt());
                    ((Player) player.get(0)).setTarget(dataIn.readObject());
                }
                // datain health
                // datain charges
                // datain action
                // datain target
            } catch (InterruptedException e) {
                System.out.println("InterruptedException from RFC run.");
            } catch (IOException e) {
                System.out.println("IO Exception from RFC run()");
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
