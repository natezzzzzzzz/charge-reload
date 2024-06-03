public class Charge extends Action{
    public Charge(String name, int cost){
        super(name, cost);
    }

    public void playerCharge(Player p){
        p.setCharges(p.getCharges()+1);
    }
}