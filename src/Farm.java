import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Farmland
 * @author Nathan Stefanik
 * @version 1.2
 */
public class Farm {
    private Controller controller;
    private List<Crop> crops;
    private int day;
    private int numPlots;

    /**
     * Constructor for a farm
     * @param controller the farm's controller
     * @param numPlots number of plots for farm
     */
    public Farm(Controller controller, int numPlots) {
        this.controller = controller;
        this.day = 1;
        this.crops = createInitialCrops();
        this.numPlots = numPlots;
    }

    /**
     * Creates an initial list of plots with 5 random plots at random stages of
     * growth and the rest empty.
     * @return the list of plots.
     */
    private List<Crop> createInitialCrops() {
        ArrayList<Crop> crops = new ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            crops.add(new Crop(controller, "", i));
        }
        ArrayList<Integer> randomIndices = new ArrayList<>(4);
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            while (randomIndices.size() == i) {
                Integer attempt = rand.nextInt(16);
                if (!randomIndices.contains(attempt)) {
                    randomIndices.add(attempt);
                }
            }
        }
        for (Integer index : randomIndices) {
            int age = rand.nextInt(4);
            crops.get(index).setType(getRandomType());
            crops.get(index).setAge(age);
            crops.get(index).setWaterLevel(1);
        }
        return crops;
    }

    /**
     * Gets a random crop type.
     * @return A String with crop type.
     */
    private String getRandomType() {
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
     * Harvests plot if mature.
     * @param cropIndex plot to harvest.
     * @return a crop if harvested, null otherwise.
     */
    public Crop harvest(int cropIndex) {
        if (crops.get(cropIndex).isHarvestable()
                && controller.getInventory().getAmount() < 20) {
            Crop harvested = crops.get(cropIndex);
            crops.set(cropIndex, new Crop(controller, "", cropIndex));
            return harvested;
        } else {
            return null;
        }
    }

    /**
     * Manipulates the necessary data when a crop is planted.
     * @param cropIndex The index of the crop planted.
     * @param inventoryIndex The index in the inventory.
     * @param type The crop type for the planted crop.
     */
    public void plant(String type, int cropIndex, int inventoryIndex) {
        crops.get(cropIndex).setType(type);
        crops.get(cropIndex).setAge(0);
        crops.get(cropIndex).setWaterLevel(1);
        crops.get(cropIndex).setIsDead(false);
        crops.get(cropIndex).setDeadBoi(false);
        controller.getInventory().getInventoryOfCrops().remove(inventoryIndex);
    }

    /**
     * Getter for crops.
     * @return the list of crops
     */
    public List<Crop> getCrops() {
        return crops;
    }

    /**
     * Getter for day
     * @return the current day number
     */
    public int getDay() {
        return day;
    }

    /**
     * Setter for day
     * @param day the day to be set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Getter for number of plots of farm
     * @return the number of plots
     */
    public int getNumPlots() {
        return numPlots;
    }
}