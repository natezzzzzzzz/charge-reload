public class Weapon extends Action{
    public int damage; //how much damage the weapon inflicts

    /**
     * This constructor creates an instance of a weapon object with specified name, cost, and damage
     *
     * @param name
     * name of the weapon
     * @param cost
     * cost of the weapon to use in charges
     * @param damage
     * damage the weapon inflicts
     */
    public Weapon(String name, int cost, int damage) {
        super(name, cost);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
