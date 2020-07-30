import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.ArrayList;
import java.util.List;

/**
 * Inventory class to store inventory data
 * @author David, Rachel, Nathan, and Kevin
 * @version 1.2
 */
public class Inventory {
    private Controller controller;
    /** how many items are currently in the inventory */
    private int amount = 0;
    private List<Crop> inventoryOfCrops = new ArrayList<>();

    /**
     * Constructor taking the main controller object.
     * @param controller the controller object
     */
    public Inventory(Controller controller) {
        this.controller = controller;
        for (int i = 0; i < 5; i++) {
            // adds 5 of the selected starting seed in the player config screen to the inventory
            inventoryOfCrops.add(new Crop(controller, controller.getPlayer().getStartingSeed(),
                    -1));
            amount++;
        }
    }

    /**
     * Adds crop to the arraylist of inventory of crops.
     * @param crop the crop to add to the inventory.
     */
    public void add(Crop crop) {
        if (amount < 20) {
            if (crop.isDeadBoi()) {
                return;
            } else {
                inventoryOfCrops.add(crop);
                amount++;
            }
        } else {
            Alert tooFull = new Alert(Alert.AlertType.ERROR, "Inventory space full!");
            tooFull.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
    }

    /**
     * Manipulates the necessary data when an item is sold from the inventory.
     * @param itemIndex The index of the item being sold.
     */
    public void sell(int itemIndex) {
        int sellPrice = inventoryOfCrops.get(itemIndex).getSellPrice();
        //add money to money account
        controller.getPlayer().setMoney(controller.getPlayer().getMoney() + sellPrice);
        //delete it from the inventory
        inventoryOfCrops.remove(itemIndex);
        amount--;
    }

    /**
     * Getter for the number of crops in the inventory
     * @return number of crops
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Getter for Inventory of Crops
     * @return inventoryOfCrops
     */
    public List<Crop> getInventoryOfCrops() {
        return inventoryOfCrops;
    }
}