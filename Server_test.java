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
    // player at index 4 --> read thread
    // player at index 5 --> write thread

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
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());

                playercount++;
                out.writeInt(playercount);
                System.out.println("Player " + playercount + " has connected!");

                ReadFromClient rfc = new ReadFromClient(playercount, in);
                WriteToClient wtc = new WriteToClient(playercount, out);

                Scanner input = new Scanner(System.in);

                System.out.println("Enter name:");
                String name = input.nextLine();
                LinkedList<Object> player_data = new LinkedList<Object>();
                player_data.add(new Player(name,playercount));
                player_data.add(s);
                player_data.add(rfc);
                player_data.add(wtc);
                players.add(player_data);
                }
            //send start msg for each player
            for(LinkedList<Object> player: players){
                ((WriteToClient) player.get(3)).sendStartMsg();
                ((ReadFromClient) player.get(2)).getPlayerDetails();
            }
            //wait ready player

            System.out.println("No longer accepting connections.");
        } catch (IOException e) {
            System.out.println("IOException from acceptPlayers().");
        }
    }

    private class ReadFromClient implements Runnable {

        private int playerID;
        private ObjectInputStream ObjectIn;

        public ReadFromClient(int id, ObjectInputStream in) {
            playerID = id;
            ObjectIn = in;
        }

        /**
         * Runs actual game after both players join
         */
        public void run() {
            try {
                for(LinkedList<Object> player : players){
                    ((Player) player.get(0)).setHealth(ObjectIn.readInt());
                    ((Player) player.get(0)).setCharges(ObjectIn.readInt());
                    ((Player) player.get(0)).setAction((ActionType)ObjectIn.readObject());
                    ((Player) player.get(0)).setTarget((Player)ObjectIn.readObject());
                    ((Player) player.get(0)).setBlock(ObjectIn.readInt());
                    ((Player) player.get(0)).setReflect(ObjectIn.readInt());
                    ((Player) player.get(0)).setPoints(ObjectIn.readInt());
                }
                // datain health
                // datain charges
                // datain action
                // datain target
            } //catch (InterruptedException e) {
                //System.out.println("InterruptedException from RFC run.");}
            catch (IOException e) {
                System.out.println("IO Exception from RFC run()");
            } catch(ClassNotFoundException e){
                System.out.println("Class not found exception from WTC run()");
            }
        }

        public void getPlayerDetails() {
            // gets the player details from each client through their respective input
            // streams
            // and puts the details in a linked list to access for later
            try{
                while(true) {
                    if(playerID == 1 && playercount>1){
                        System.out.println("Is Host Ready");
                        hostStarts = ObjectIn.readBoolean();
                        break;
                    }
                }
                if(hostStarts){
                    for(LinkedList<Object> player: players){
                        ((WriteToClient)player.get(3)).sendGameStart();
                        player.add(new Thread((ReadFromClient) player.get(2)));
                        player.add(new Thread((WriteToClient) player.get(3)));
                        ((Thread) player.get(4)).start();
                        ((Thread) player.get(5)).start();
                    }
                }
            } catch (IOException e){
                System.out.println("IOException from getPlayerDetails");
            }
        }

    }

    private class WriteToClient implements Runnable {

        private int playerID;
        private ObjectOutputStream objectOut;

        public WriteToClient(int id, ObjectOutputStream out) {
            playerID = id;
            objectOut = out;

            System.out.println("WTC" + playerID + " Runnable Created");
        }

        public void run() {
            try {
                while(true){
                for (LinkedList<Object> player : players) {
                    writePlayerDetails(player);
                    //action of player
                    if(((Player) player.get(0)).getAction().equals(ActionType.CHARGE)){
                        
                    }
                }
            }
            } catch (IOException e) {
                System.out.println("IOException from WTC run.");
            }
        }

        public void writePlayerDetails(LinkedList<Object> player) throws IOException{
            // outs the details of the players to each other, not sending a player's details
            // to themself

            for(LinkedList<Object> otherPlayer : players){
                if (otherPlayer.equals(player))
                    continue;
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getHealth());
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getCharges());
                objectOut.writeObject(((Player) (otherPlayer.get(0))).getAction());
                objectOut.writeObject(((Player) (otherPlayer.get(0))).getTarget());
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getBlock());
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getReflect());
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getPoints());
                }
        }

        public void sendStartMsg() {
            try {
                objectOut.writeUTF("All players have connected. Start Game imminent");
            } catch (IOException e) {
                System.out.println("IO Exception from sendStartMsg()");
            }
        }

        public void sendGameStart() {
            try {
                objectOut.writeUTF("Host is starting...");
            } catch (IOException e) {
                System.out.println("IOException from sendGameStart()");
            }
        }

    }
}
