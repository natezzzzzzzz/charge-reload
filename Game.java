import java.util.*;
public class Game {
    private int currentPlayers; //the amount of current players in game
    private int maxPlayers; //the max amount of players
    private static ArrayList<Player> players = new ArrayList<Player>(); //list of current players
    static Charge charge = new Charge("Charge",0);
    static Weapon knife = new Weapon("Knife",0,1);
    static Weapon bang = new Weapon("Bang",1,2);
    static Weapon shotgun = new Weapon("Shotgun",2,3);

    static Block regular_block = new Block("Block",0,2);
    Block super_block = new Block("Super Block",1,4);

    static Reflect reflect = new Reflect("Reflect",0,2,false);
    Reflect super_reflect = new Reflect("Super Reflect",1,3,false);
    static Reflect counter_reflect = new Reflect("Counter",0,1,true);

    /**
     * This method finds the index of the array list using player id/name
     * @param name
     * @return
     * index of player
     */
    public static int findIndexOfPlayer(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getId().equals(name))
                return i;
        }
        return -1;
    }

    /**
     * This method is incomplete, needs work for other weapons, but this method shoots a player
     * @param p1
     * player that shoots target
     * @param p2
     * target of the bang
     *
     * Note: we can change bang to a "weapon" method that can encompass all the weapons and needs an enum type for its parameters
     */
    public static void bang(Player p1, Player p2) {
        if(p2.getBlock()>0){//if the target played a block move
            p2.setHealth(p2.getHealth() - Math.max(0,bang.getDamage()-p2.getBlock()));
            p2.setBlock(Math.max(0,p2.getBlock()-knife.getDamage()));
            System.out.println(p2.getId() + " blocks the bang!");
        }
        else if(p2.getReflect() > 1){//if the target reflects
            p1.setHealth(p1.getHealth() - bang.getDamage());//the attack goes back to the player that shoots
            System.out.println("The bang from " + p1.getId() + " gets reflected and hits back!");
            System.out.println(p1.getId() + " has " + p1.getHealth() + " hp!");
        }
        else{//if target is vulnerable, target gets hit
            p2.setHealth(p2.getHealth()-bang.getDamage());
            System.out.println(p2.getId() + " gets hit with the bang from " + p1.getId() + "! " );
            System.out.println(p2.getId() + " has " + p2.getHealth() + " hp!");
        }
    }

    /**
     * This method knives a player
     * @param p1
     * player that attacks target
     * @param p2
     * target of attack
     */
    public static void knife(Player p1, Player p2){
        if(p2.getBlock()>0){
            p2.setHealth(p2.getHealth() - Math.max(0,knife.getDamage()-p2.getBlock()));
            p2.setBlock(Math.max(0,p2.getBlock()-knife.getDamage()));
            System.out.println(p2.getId() + " blocks the knife!");
        }
        if(p2.getReflect() == 1){
            p1.setHealth(p1.getHealth() - knife.getDamage());
            System.out.println("The knife from " + p1.getId() + " gets reflected and hits back!");
            System.out.println(p1.getId() + " has " + p1.getHealth() + " hp!");
        }
        else{
            p2.setHealth(p2.getHealth()-knife.getDamage());
            System.out.println(p2.getId() + " gets hit with the knife from " + p1.getId() + "! " );
            System.out.println(p2.getId() + " has " + p2.getHealth() + " hp!");
        }
    }
    /**public Game(ArrayList<Player> players) {
     }*/
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Player p1 = new Player();
        Player p2 = new Player();
        Player p3 = new Player();

        p1.setId("p1");
        p2.setId("p2");
        p3.setId("p3");

        players.add(p1);
        players.add(p2);
        players.add(p3);

        while((!p1.isDead() && !p2.isDead()) || (!p1.isDead() && !p3.isDead()) || (!p2.isDead() && !p3.isDead())){
            String[] moves = new String[players.size()];//the moves of the players made
            for(int i = 0; i < players.size(); i++) {
                if(players.get(i).isDead())
                    continue;//if the player has died, skip their turn
                players.get(i).setTarget(null);
                players.get(i).setBlock(0);
                players.get(i).setReflect(0);
                //resets block, reflect, and targets

                System.out.println(players.get(i).getId() + " move, Health: " + players.get(i).getHealth() + " Charges: " + players.get(i).getCharges());
                moves[i] = input.nextLine();
                if(moves[i].equals("bang") || moves[i].equals("knife")) {//we should change this to an enum type for weapons so that player can target
                    if(moves[i].equals("bang") && players.get(i).getCharges() < 1){//if player doesn't have enough charge to play a weapon, (again, we can change this)
                        System.out.println("Not enough charges for this move, choose another move");
                        i -= 1;
                        continue;//goes back to their turn and lets them choose a different move
                    }
                    System.out.print("Choose target:");
                    players.get(i).setTarget(players.get(findIndexOfPlayer(input.nextLine())));
                }
            }

            for(int i = 0; i < players.size(); i++){
                if(moves[i].equalsIgnoreCase("charge")) {
                    charge.playerCharge(players.get(i));
                    System.out.println(players.get(i).getId() + " charges!");
                }
                if(moves[i].equalsIgnoreCase("block")) {
                    players.get(i).setBlock(regular_block.getBlock_amt());
                    System.out.println(players.get(i).getId() + " blocks!");
                }
                if(moves[i].equalsIgnoreCase("reflect")) {
                    players.get(i).setReflect(reflect.getReflect_amt());
                    System.out.println(players.get(i).getId() + " reflects!");
                }
                if(moves[i].equalsIgnoreCase("counter")) {
                    players.get(i).setReflect(counter_reflect.getReflect_amt());
                    System.out.println(players.get(i).getId() + " counters!");
                }
            }
            for(int i = 0; i < players.size(); i++){
                if(moves[i].equalsIgnoreCase("bang")) {
                    try {
                        players.get(i).setCharges(players.get(i).getCharges()-1);
                        if (players.get(findIndexOfPlayer(players.get(i).getTarget().getId())).getTarget().getId().equals(players.get(i).getId()) && moves[findIndexOfPlayer(players.get(i).getTarget().getId())].equalsIgnoreCase("bang"))
                            System.out.println("Each player bangs! The bullet ricochets and no one gets hit");
                        else
                            bang(players.get(i), players.get(i).getTarget());
                    } catch (NullPointerException e) {
                        players.get(i).setCharges(players.get(i).getCharges()-1);
                        bang(players.get(i), players.get(i).getTarget());
                    }
                }
                if(moves[i].equalsIgnoreCase("knife")) {
                    try {
                        if (players.get(findIndexOfPlayer(players.get(i).getTarget().getId())).getTarget().getId().equals(players.get(i).getId()) && moves[findIndexOfPlayer(players.get(i).getTarget().getId())].equalsIgnoreCase("knife"))
                            System.out.println("Each player slashes! The knives break and no one gets hit");
                        else
                            knife(players.get(i), players.get(i).getTarget());
                    } catch (NullPointerException e) {
                        knife(players.get(i), players.get(i).getTarget());
                    }
                }
            }
        }

        System.out.println("Game over" +
                "\n" + p1.getId() + " hp: " + p1.getHealth() +
                "\n" + p2.getId() + " hp: " + p2.getHealth());
    }
}
