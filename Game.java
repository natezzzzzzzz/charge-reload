import java.util.*;

public class Game {
    private int currentPlayers; // the amount of current players in game
    private int maxPlayers; // the max amount of players
    private ArrayList<Player> players = new ArrayList<Player>(); // list of current players
    private Charge charge = new Charge("Charge", 0);
    private Weapon knife = new Weapon("Knife", 0, 1);
    private Weapon bang = new Weapon("Bang", 1, 2);
    private Weapon shotgun = new Weapon("Shotgun", 2, 3);

    private Block regular_block = new Block("Block", 0, 2);
    private Block super_block = new Block("Super Block", 1, 4);

    private Reflect reflect = new Reflect("Reflect", 0, 2, false);
    private Reflect super_reflect = new Reflect("Super Reflect", 1, 3, false);
    private Reflect counter_reflect = new Reflect("Counter", 0, 1, true);

    public Game(ArrayList<Player> players) {
        this.players = players;

    }

    /**
     * This method finds the index of the array list using player id/name
     * 
     * @param name
     *             name/id of the player
     * @return
     *         index of player
     */
    public int findIndexOfPlayer(String name) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    /**
     * This method is incomplete, needs work for other weapons, but this method
     * shoots a player
     * 
     * @param p1
     *           player that shoots target
     * @param p2
     *           target of the bang
     */
    public String projectile(Player p1, Player p2, ActionType action) {
        Weapon w = (Weapon) EnumTypeToAction(action);
        p1.setCharges(p1.getCharges() - w.getCost());
        if (EnumTypeToAction(p2.getAction()) instanceof Weapon && p2.getTarget().getId() == p1.getId()) {
            int dmg = Math.max(0, w.getDamage() - ((Weapon) EnumTypeToAction(p2.getAction())).getDamage());
            p2.setHealth(p2.getHealth() - dmg);
            String out = p1.getName() + " attacks " + p2.getName() + " with a " + w.getName() + "! But " + p2.getName()
                    + " attacks back with a " + ((Weapon) EnumTypeToAction(p2.getAction())).getName() + "!\n"
                    + p2.getName() + " has " + p2.getHealth() + " hp!";
            return out;
        }
        if (p2.getBlock() > 0) {// if the target played a block move
            p2.setHealth(p2.getHealth() - Math.max(0, w.getDamage() - p2.getBlock()));
            p2.setBlock(Math.max(0, p2.getBlock() - w.getDamage()));

            String out = p2.getName() + " blocks the " + w.getName() + "!";
            return out;
            // System.out.println(p2.getId() + " blocks the bang!");
        } else if (p2.getReflect() > (w.getDamage() - 1)) {// if the target reflects
            p1.setHealth(p1.getHealth() - w.getDamage());// the attack goes back to the player that shoots

            String out = "The " + w.getName() + " from " + p1.getName() + " gets reflected and hits back!" + "\n"
                    + p1.getName() + " has " + p1.getHealth() + " hp!";
            return out;
            // System.out.println("The bang from " + p1.getId() + " gets reflected and hits
            // back!");
            // System.out.println(p1.getId() + " has " + p1.getHealth() + " hp!");
        } else {// if target is vulnerable, target gets hit
            p2.setHealth(p2.getHealth() - w.getDamage());

            String out = p2.getName() + " gets hit with the " + w.getName() + " from " + p1.getName() + "! " + "\n"
                    + p2.getName() + " has " + p2.getHealth() + " hp!";
            return out;
            // System.out.println(p2.getId() + " gets hit with the bang from " + p1.getId()
            // + "! ");
            // System.out.println(p2.getId() + " has " + p2.getHealth() + " hp!");
        }
    }

    /**
     * This method knives a player
     * 
     * @param p1
     *           player that attacks target
     * @param p2
     *           target of attack
     */
    public String knife(Player p1, Player p2) {
        if (EnumTypeToAction(p2.getAction()) instanceof Weapon && p2.getTarget().getId() == p1.getId()) {
            int dmg = Math.max(0, knife.getDamage() - ((Weapon) EnumTypeToAction(p2.getAction())).getDamage());
            p2.setHealth(p2.getHealth() - dmg);
            String out = p1.getName() + " attacks " + p2.getName() + " with a " + knife.getName() + "! But "
                    + p2.getName()
                    + " attacks back with a " + ((Weapon) EnumTypeToAction(p2.getAction())).getName() + "!\n"
                    + p2.getName() + " has " + p2.getHealth() + " hp!";
            return out;
        }
        if (p2.getBlock() > 0) {
            p2.setHealth(p2.getHealth() - Math.max(0, knife.getDamage() - p2.getBlock()));
            p2.setBlock(Math.max(0, p2.getBlock() - knife.getDamage()));

            String out = p2.getName() + " blocks the knife!";
            return out;
            // System.out.println(p2.getId() + " blocks the knife!");
        }
        if (p2.getReflect() == 1) {
            p1.setHealth(p1.getHealth() - knife.getDamage());

            String out = "The knife from " + p1.getName() + " gets reflected and hits back!" + "\n" + p1.getName()
                    + " has "
                    + p1.getHealth() + " hp!";
            return out;
            // System.out.println("The knife from " + p1.getId() + " gets reflected and hits
            // back!");
            // System.out.println(p1.getId() + " has " + p1.getHealth() + " hp!");
        } else {
            p2.setHealth(p2.getHealth() - knife.getDamage());

            String out = p2.getName() + " gets hit with the knife from " + p1.getName() + "! " + "\n" + p2.getName()
                    + " has "
                    + p2.getHealth() + " hp!";
            return out;
            // System.out.println(p2.getId() + " gets hit with the knife from " + p1.getId()
            // + "! ");
            // System.out.println(p2.getId() + " has " + p2.getHealth() + " hp!");
        }
    }

    /**
     * This method gives block to the player of any type
     * 
     * @param p
     *               player that uses and receives block
     * @param action
     *               the type of block the player uses
     * @return
     *         A String representation of the output of the player using block
     */
    public String block(Player p, ActionType action) {
        Block b = (Block) EnumTypeToAction(action);
        p.setBlock(b.getBlock_amt());
        p.setCharges(p.getCharges() - b.getCost());
        String out = p.getName() + " " + b.getName() + " s!";
        return out;
        // switch (action) {
        // case REGULAR_BLOCK:
        // p.setBlock(regular_block.getBlock_amt());
        // String out = p.getId() + " blocks!";
        // return out;
        // case
        // }
    }

    /**
     * This method is used when players play a reflect action
     */
    public String reflect(Player p, ActionType action) {
        Reflect r = (Reflect) EnumTypeToAction(action);
        p.setReflect(r.getReflect_amt());
        p.setCharges(p.getCharges() - r.getCost());
        String out = p.getName() + " " + r.getName() + " s!";
        return out;
    }

    public Action EnumTypeToAction(ActionType e) {
        switch (e) {
            case REGULAR_BLOCK:
                return (Block) regular_block;
            case SUPER_BLOCK:
                return (Block) super_block;
            case CHARGE:
                return (Charge) charge;
            case REFLECT:
                return (Reflect) reflect;
            case COUNTER:
                return (Reflect) counter_reflect;
            case SUPER_REFLECT:
                return (Reflect) super_reflect;
            case KNIFE:
                return (Weapon) knife;
            case BANG:
                return (Weapon) bang;
            case SHOTGUN:
                return (Weapon) shotgun;
            default:
                return (Charge) charge;
        }
    }

    /**
     * public Game(ArrayList<Player> players) {
     * }
     */
    // public static void main(String[] args) {
    // Scanner input = new Scanner(System.in);

    // Player p1 = new Player();
    // Player p2 = new Player();
    // Player p3 = new Player();

    // p1.setId("p1");
    // p2.setId("p2");
    // p3.setId("p3");

    // players.add(p1);
    // players.add(p2);
    // players.add(p3);

    // while ((!p1.isDead() && !p2.isDead()) || (!p1.isDead() && !p3.isDead()) ||
    // (!p2.isDead() && !p3.isDead())) {
    // String[] moves = new String[players.size()];// the moves of the players made
    // for (int i = 0; i < players.size(); i++) {
    // if (players.get(i).isDead())
    // continue;// if the player has died, skip their turn
    // players.get(i).setTarget(null);
    // players.get(i).setBlock(0);
    // players.get(i).setReflect(0);
    // // resets block, reflect, and targets

    // System.out.println(players.get(i).getId() + " move, Health: " +
    // players.get(i).getHealth()
    // + " Charges: " + players.get(i).getCharges());
    // moves[i] = input.nextLine();
    // if (moves[i].equals("bang") || moves[i].equals("knife")) {// we should change
    // this to an enum type for
    // // weapons so that player can target
    // if (moves[i].equals("bang") && players.get(i).getCharges() < 1) {// if player
    // doesn't have enough
    // // charge to play a weapon, (again,
    // // we can change this)
    // System.out.println("Not enough charges for this move, choose another move");
    // i -= 1;
    // continue;// goes back to their turn and lets them choose a different move
    // }
    // System.out.print("Choose target:");
    // players.get(i).setTarget(players.get(findIndexOfPlayer(input.nextLine())));
    // }
    // }

    // for (int i = 0; i < players.size(); i++) {
    // if (moves[i].equalsIgnoreCase("charge")) {
    // charge.playerCharge(players.get(i));
    // System.out.println(players.get(i).getId() + " charges!");
    // }
    // if (moves[i].equalsIgnoreCase("block")) {
    // players.get(i).setBlock(regular_block.getBlock_amt());
    // System.out.println(players.get(i).getId() + " blocks!");
    // }
    // if (moves[i].equalsIgnoreCase("reflect")) {
    // players.get(i).setReflect(reflect.getReflect_amt());
    // System.out.println(players.get(i).getId() + " reflects!");
    // }
    // if (moves[i].equalsIgnoreCase("counter")) {
    // players.get(i).setReflect(counter_reflect.getReflect_amt());
    // System.out.println(players.get(i).getId() + " counters!");
    // }
    // }
    // for (int i = 0; i < players.size(); i++) {
    // if (moves[i].equalsIgnoreCase("bang")) {
    // try {
    // players.get(i).setCharges(players.get(i).getCharges() - 1);
    // if
    // (players.get(findIndexOfPlayer(players.get(i).getTarget().getId())).getTarget().getId()
    // .equals(players.get(i).getId())
    // && moves[findIndexOfPlayer(players.get(i).getTarget().getId())]
    // .equalsIgnoreCase("bang"))
    // System.out.println("Each player bangs! The bullet ricochets and no one gets
    // hit");
    // else
    // bang(players.get(i), players.get(i).getTarget());
    // } catch (NullPointerException e) {
    // players.get(i).setCharges(players.get(i).getCharges() - 1);
    // bang(players.get(i), players.get(i).getTarget());
    // }
    // }
    // if (moves[i].equalsIgnoreCase("knife")) {
    // try {
    // if
    // (players.get(findIndexOfPlayer(players.get(i).getTarget().getId())).getTarget().getId()
    // .equals(players.get(i).getId())
    // && moves[findIndexOfPlayer(players.get(i).getTarget().getId())]
    // .equalsIgnoreCase("knife"))
    // System.out.println("Each player slashes! The knives break and no one gets
    // hit");
    // else
    // knife(players.get(i), players.get(i).getTarget());
    // } catch (NullPointerException e) {
    // knife(players.get(i), players.get(i).getTarget());
    // }
    // }
    // }
    // }

    // System.out.println("Game over" +
    // "\n" + p1.getId() + " hp: " + p1.getHealth() +
    // "\n" + p2.getId() + " hp: " + p2.getHealth());
    // }
}
