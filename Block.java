public class Block extends Action{
    public int block_amt; //amount of damage to block

    /**
     * This constructor creates an instance of the block object with specified name, cost, and blocked amount
     * @param name
     * @param cost
     * @param block_amt
     */
    public Block(String name, int cost, int block_amt) {
        super(name, cost);
        this.block_amt = block_amt;
    }

    public int getBlock_amt() {
        return block_amt;
    }

    public void setBlock_amt(int block_amt) {
        this.block_amt = block_amt;
    }
}
