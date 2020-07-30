import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.ArrayList;
import java.util.Random;

/**
 * Market class to store data for the market
 * @author Nathan, Kevin, David, and Rachel
 * @version 1.2
 */
public class Market {
    private Controller controller;
    private ArrayList<MarketItem> itemsForSale;

    /**
     * Assigns the instance variable controller to the parameter.
     * @param controller The controller passed in.
     */
    public Market(Controller controller) {
        this.controller = controller;
        this.itemsForSale = generateItems();
    }

    /**
     * Loops through the items for sale and adds them to list.
     * @return An ArrayList of Market Items.
     */
    private ArrayList<MarketItem> generateItems() {
        Random rand = new Random();
        int numberOfItemsForSale = rand.nextInt(7) + 2; // 2 to 8
        itemsForSale = new ArrayList<>(numberOfItemsForSale);
        for (int i = 0; i < numberOfItemsForSale; i++) {
            itemsForSale.add(new MarketItem(controller));
        }
        return itemsForSale;
    }

    /**
     * Runs through necessary data changes when an item is bought.
     * @param itemIndex The index for the item being bought.
     */
    public void buy(int itemIndex) {
        MarketItem item = itemsForSale.get(itemIndex);
        //check if they have enough money to buy this
        int price = item.getPrice();
        boolean enoughMoney = controller.getPlayer().getMoney() - price >= 0;
        boolean enoughInventory = controller.getInventory().getAmount() <= 19;
        if (enoughMoney && enoughInventory) {
            //first needs to make picture and writing disappear from market
            controller.getMarket().getItemsForSale().remove(itemIndex);
            //needs to add item to inventory
            Crop c = new Crop(controller, item.getSeedType(), -1);
            controller.getInventory().add(c);
            //needs to subtract money from current money balance
            controller.getPlayer().setMoney(controller.getPlayer().getMoney()
                    - item.getPrice());
        } else if (controller.getInventory().getAmount() >= 20) {
            Alert inventoryFull = new Alert(Alert.AlertType.ERROR, "Inventory is full");
            inventoryFull.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        } else {
            Alert notEnoughMoney = new Alert(Alert.AlertType.ERROR, "Not enough money to purchase");
            notEnoughMoney.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
    }

    /**
     * On called it returns the items for sale in the market.
     * @return The ArrayList of Market Items that are for sale.
     */
    public ArrayList<MarketItem> getItemsForSale() {
        return itemsForSale;
    }
}