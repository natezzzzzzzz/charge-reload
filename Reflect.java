public class Reflect extends Action{
    public int reflect_amt;
    public boolean counter;
    /**
     * This constructor creates an instance
     * @param name
     * @param cost
     * @param reflect_amt
     */
    public Reflect(String name, int cost, int reflect_amt, boolean counter){
        super(name, cost);
        this.reflect_amt = reflect_amt;
        this.counter = counter;
    }

    public int getReflect_amt() {
        return reflect_amt;
    }

    public void setReflect_amt(int reflect_amt) {
        this.reflect_amt = reflect_amt;
    }

    public boolean isCounter() {
        return counter;
    }

    public void setCounter(boolean counter) {
        this.counter = counter;
    }
}
