public class Player {
    private int id; // name or id of the user
    private int health = 2; // health of the player, default to 2
    private int charges = 0; // amount of charges of the player, default to 0
    private int block = 0;
    private int reflect = 0;
    private int points = 0; // amount of points of the player, default to 0
    private Player target;
    private ActionType action;
    private String name;

    /**
     * This constructor creates an instance of the player object
     */
    public Player(String n, int i) {
        id = i;
        name = n;
        health = 2;
        charges = 0;
        block = 0;
        reflect = 0;
        points = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
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

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    //FINISH THIS
    public void setActionFromString(String action){
        switch(action){
            case "Charge":
                this.action = ActionType.CHARGE;
                break;
            case "Bang":
                this.action = ActionType.BANG;
                break;
            case "Knife":
                this.action = ActionType.KNIFE;
                break;
            case "Shotgun":
                this.action = ActionType.SHOTGUN;
                break;
            case "RegularBlock":
                this.action = ActionType.REGULAR_BLOCK;
                break;
            case "SuperBlock":
                this.action = ActionType.SUPER_BLOCK;
                break;
            case 
        }
    }

}