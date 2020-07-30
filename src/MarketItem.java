import java.util.Random;

/**
 * Class for a Market Item
 * @author David, Kevin, Rachel, and Nathan
 * @version 1.0
 */
public class MarketItem {
    private Controller controller;
    private String seedType;
    private int price;

    /**
     * Constructor taking the main controller object.
     * @param controller the controller object
     */
    public MarketItem(Controller controller) {
        this.controller = controller;
        this.seedType = getRandomSeed();
        this.price = createPrice(controller, seedType);
    }

    /**
     * Generates a price based on crop type.
     * @param controller the controller object
     * @param cropType the type of crop given
     * @return An int for the price.
     */
    public static int createPrice(Controller controller, String cropType) {
        int basePrice;
        double difficultyMultiplier;
        if (cropType.equals("Wheat")) {
            basePrice = 10;
        } else if (cropType.equals("Carrots")) {
            basePrice = 15;
        } else {
            basePrice = 20;
        }
        int playerDifficulty = controller.getPlayer().getDifficulty();
        if (playerDifficulty == 0) {
            difficultyMultiplier = 1.0;
        } else if (playerDifficulty == 1) {
            difficultyMultiplier = 1.5;
        } else {
            difficultyMultiplier = 2.0;
        }
        Random rand = new Random();
        int randomAdjustment = rand.nextInt(11) - 5;
        return (int) (basePrice * difficultyMultiplier + randomAdjustment);
    }

    /**
     * Gets the selling price for the item.
     * @param controller the controller object
     * @param cropType the type of crop
     * @return the sell price.
     */
    public static int getItemSellingPrice(Controller controller, String cropType) {
        Random rand = new Random();
        double harvestMultiplier = (rand.nextInt(11) + 10) * 0.1; // 1.0, 1.1, ..., 1.9, 2.0
        return (int) (createPrice(controller, cropType) * harvestMultiplier);
    }

    /**
     * Gets a random type of crop.
     * @return A String of the random crop type.
     */
    private String getRandomSeed() {
        Random rand = new Random();
        int randomSeed = rand.nextInt(3);
        if (randomSeed == 0) {
            return "Wheat";
        } else if (randomSeed == 1) {
            return "Carrots";
        } else {
            return "Potatoes";
        }
    }

    /**
     * Gets the seed type.
     * @return the seed type.
     */
    public String getSeedType() {
        return seedType;
    }

    /**
     * Gets the price.
     * @return the price.
     */
    public int getPrice() {
        return price;
    }
}