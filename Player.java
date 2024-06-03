public class Player {
    private String id; // name or id of the user
    private int health = 2; // health of the player, default to 2
    private int charges = 0; // amount of charges of the player, default to 0
    private int block = 0;
    private int reflect = 0;
    private int points = 0; // amount of points of the player, default to 0
    private Player target;

    /**
     * This constructor creates an instance of the player object
     */
    public Player() {
        health = 2;
        charges = 0;
        block = 0;
        reflect = 0;
        points = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getReflect() {
        return reflect;
    }

    public void setReflect(int reflect) {
        this.reflect = reflect;
    }

    public Player getTarget() throws NullPointerException {
        if (target == null) {
            throw new NullPointerException();
        }
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

}