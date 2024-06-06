import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.*;

public class Server_test {

    private ServerSocket ss;
    private int playercount;
    private final int playermax = 2;
    private boolean hostStarts;

    private Game game;
    private String gameplayString;

    private ArrayList<LinkedList> players = new ArrayList<>();
    // Data for players and for each player (each player is a linked list)↓↓
    // player at index 0 --> player object with specified name and id with default
    // health, charges, etc
    // player at index 1 --> socket
    // player at index 2 --> rfc, read runnable
    // player at index 3 --> wfc, write runnable
    // player at index 4 --> read thread
    // player at index 5 --> write thread
    // players.get(1).get(2) --> player 2's rfc

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
            while (hostStarts || playercount < playermax) {
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
                Thread readThread = new Thread(rfc);
                Thread writeThread = new Thread(wtc);
                player_data.add(new Player(name, playercount)); // index 0
                player_data.add(s); // index 1
                player_data.add(rfc); // index 2
                player_data.add(wtc); // index 3
                player_data.add(readThread); // index 4
                player_data.add(writeThread); // index 5
                players.add(player_data);

                // ((WriteToClient) player.get(3)).sendStartMsg();
            }
            // send start msg for each player
            // after host starts game ??

            if (playercount == 1) {
                ((ReadFromClient) players.get(0).get(2)).getPlayerDetails();
            }

            // wait ready player

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
                while (true) {
                    for (LinkedList<Object> player : players) {
                        if(((Player)player.get(0)).getHealth() < 1)
                            continue;
                        ((Player) player.get(0)).setHealth(ObjectIn.readInt());
                        ((Player) player.get(0)).setCharges(ObjectIn.readInt());
                        ((Player) player.get(0)).setAction((ActionType) ObjectIn.readObject());
                        ((Player) player.get(0)).setTarget((Player) ObjectIn.readObject());
                        ((Player) player.get(0)).setBlock(ObjectIn.readInt());
                        ((Player) player.get(0)).setReflect(ObjectIn.readInt());
                        ((Player) player.get(0)).setPoints(ObjectIn.readInt());
                        // player.set(0, (Player) ObjectIn.readObject());
                    }

                    //non weapon actions for each player
                    for (LinkedList<Object> player : players) {
                        if(((Player)player.get(0)).getHealth() < 1)
                            continue;
                        // objectOut.writeUTF(gameplay details)
                        //Player didn't have action/ran out of time
                        if (((Player) player.get(0)).getAction() == null)
                            gameplayString += ((Player) player.get(0)).getName() + " didn't make a move in time. "+ game.charge((Player) player.get(0)) + "\n";
                        // Player charges
                        if (((Player) player.get(0)).getAction() == ActionType.CHARGE ) {
                            gameplayString += game.charge((Player) player.get(0)) + "\n";
                        }
                        //Player uses reflect
                        if (((Player) player.get(0)).getAction() == ActionType.REFLECT || ((Player) player.get(0)).getAction() == ActionType.SUPER_REFLECT) {
                            gameplayString += game.reflect(((Player) player.get(0)),((Player) player.get(0)).getAction()) + "\n";
                        }
                        //Player uses block
                        if (((Player) player.get(0)).getAction() == ActionType.REGULAR_BLOCK || ((Player) player.get(0)).getAction() == ActionType.SUPER_BLOCK) {
                            gameplayString += game.block(((Player) player.get(0)),((Player) player.get(0)).getAction())    + "\n";
                        }
                    }

                    for (LinkedList<Object> player : players) {
                        if(((Player)player.get(0)).getHealth() < 1)
                            continue;
                        // Player knives and targets a player
                        if (((Player) player.get(0)).getAction() == ActionType.KNIFE) {
                            gameplayString += game.knife(((Player) player.get(0)),((Player) player.get(0)).getTarget()) + "\n";
                        }
                        // Player uses a projectile and targets a player
                        if (((Player) player.get(0)).getAction() == ActionType.BANG || ((Player) player.get(0)).getAction() == ActionType.SHOTGUN){
                            gameplayString += game.projectile(((Player) player.get(0)),((Player) player.get(0)).getTarget(),((Player) player.get(0)).getAction()) + "\n";
                        }
                    }

                    Thread.sleep(6000);
                }
            } catch (IOException e) {
                System.out.println("IO Exception from RFC run()");
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found exception from WTC run()");
            } catch (InterruptedException e) {
                System.out.println("InterruptedException from RFC run()");
            }
        }

        public void getPlayerDetails() {
            // gets the player details from each client through their respective input
            // streams
            // and puts the details in a linked list to access for later
            try {
                while (playerID == 1) {
                    hostStarts = ObjectIn.readBoolean();

                    if (hostStarts && players.size() > 1) {
                        System.out.println("Host is Ready.. Game Starting..");
                        break;
                    }
                }

                // game starts here
                if (hostStarts) {
                    ArrayList<Player> objPlayers = new ArrayList<Player>();
                    for (LinkedList<Object> player : players) {
                        ((WriteToClient) player.get(3)).sendGameStart();
                        // player.add(new Thread((ReadFromClient) player.get(2)));
                        // player.add(new Thread((WriteToClient) player.get(3)));
                        ((Thread) player.get(4)).start();
                        ((Thread) player.get(5)).start();
                        objPlayers.add((Player) player.get(0));
                    }

                    game = new Game(objPlayers);
                }
            } catch (IOException e) {
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
                while (true) {
                    for (LinkedList<Object> player : players) {
                        writePlayerDetails(player);

                        // // action of player
                        // if (((Player) player.get(0)).getAction().equals(ActionType.CHARGE)) {
                        // // objectOut.writeUTF(gameplay details);
                        // objectOut.writeUTF(game.charge(player1));
                        // }

                        objectOut.writeUTF(gameplayString);
                    }
                    gameplayString = "";
                    for (LinkedList<Object> p : players) {
                        ((Player) p.get(0)).setAction(ActionType.CHARGE);
                    }
                    Thread.sleep(6000);

                    //check if one person is alive
                    Player alive = null;//the variable for the person alive
                    boolean gameEnd = false;//if the game has ended
                    for(LinkedList<Object> player: players){
                        if(alive != null && ((Player)player.get(0)).getHealth() > 0){//if there's two people alive, game has not ended
                            gameEnd = false;
                            break;
                        }
                        if(((Player)player.get(0)).getHealth() > 0){//if one person is alive, game ended
                            gameEnd = true;
                            alive = (Player)player.get(0);
                        }
                    }
                    if(alive == null)//if no one is alive, then game ends in a tie
                        gameEnd = true;
                    objectOut.writeBoolean(gameEnd);//write to GUI, if null then game is a tie
                    objectOut.writeObject(alive);
                    
                }
            } catch (IOException e) {
                System.out.println("IOException from WTC run.");
            } catch (InterruptedException e) {
                System.out.println("InterruptedException from WTC run.");
            }
        }

        public void writePlayerDetails(LinkedList<Object> player) throws IOException {
            // outs the details of the players to each other, not sending a player's details
            // to themself

            for (LinkedList<Object> otherPlayer : players) {
                if (otherPlayer.equals(player))
                    continue;
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getHealth());
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getCharges());
                objectOut.writeObject(((Player) (otherPlayer.get(0))).getAction());
                objectOut.writeObject(((Player) (otherPlayer.get(0))).getTarget());
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getBlock());
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getReflect());
                objectOut.writeInt(((Player) (otherPlayer.get(0))).getPoints());
                // objectOut.writeObject((Player) (otherPlayer.get(0)));
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
