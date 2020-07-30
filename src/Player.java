/**
 * Class for Player
 * @author David, Kevin, Rachel, and Nathan
 * @version 1.0
 */
public class Player {
    private Controller controller;
    private String name;
    private int difficulty; // 0 (easy), 1 (medium), or 2 (hard)
    private Inventory inventory;
    private int money;
    private String startingSeed;

    /**
     * Constructor taking the main controller object.
     * @param controller the controller object
     */
    public Player(Controller controller) {
        this.controller = controller;
    }

    /**
     * Sets the initial money based on difficulty.
     */
    public void setInitialMoney() {
        if (difficulty == 0) {
            this.money = 100;
        } else if (this.difficulty == 1) {
            this.money = 75;
        } else {
            this.money = 50;
        }
    }

    /**
     * Getter for the money.
     * @return the money.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Setter for the money.
     * @param money the money.
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Getter for the difficulty.
     * @return the difficulty.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Setter for the difficulty.
     * @param difficulty the difficulty.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Setter for the inventory.
     * @param inventory the inventory passed.
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Setter for the name.
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the seed type.
     * @return the starting seed type.
     */
    public String getStartingSeed() {
        return startingSeed;
    }

    /**
     * Setter for the starting seed type.
     * @param startingSeed the starting seed type.
     */
    public void setStartingSeed(String startingSeed) {
        this.startingSeed = startingSeed;
    }
}