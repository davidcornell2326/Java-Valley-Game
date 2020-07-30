import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.Random;

/**
 * Controller class
 * @author David, Nathan, Rachel, and Kevin
 * @version 1.2
 */
public class Controller {
    private Stage primaryStage;
    private Inventory inventory;
    private Player player;
    private Farm farm;
    private Market market;
    private int day = 1;
    private String newType;

    /**
     * Constructor taking a universal primary stage.
     * @param primaryStage the universal stage
     */
    public Controller(Stage primaryStage) {
        this.primaryStage = primaryStage;
        InitialWelcomePage welcomeUI = new InitialWelcomePage(this);
        welcomeUI.start(primaryStage);
    }

    /**
     * Procedure to set up player's data.
     * @param name the player's name (from the UI)
     * @param difficulty the game's difficulty (from the UI)
     * @param startingSeed the player's starting seed (from the UI)
     */
    public void setUpGame(String name, int difficulty, String startingSeed) {
        this.player = new Player(this);
        player.setName(name);
        player.setDifficulty(difficulty);
        player.setStartingSeed(startingSeed);
        this.inventory = new Inventory(this); // this MUST be after setStartingSeed has happened
        player.setInventory(inventory);
        player.setInitialMoney();
        this.market = new Market(this);
        this.farm = new Farm(this, 16); // also sets up initial crops

    }

    /**
     * Opens the Player Configuration Screen.
     */
    public void startPlayerConfiguration() {
        PlayerConfigurationScreen configUI = new PlayerConfigurationScreen(this);
        configUI.start(primaryStage);
    }

    /**
     * Opens the Farm (Main) screen.
     */
    public void goToFarm() {
        Main farmUI = new Main(this);
        farmUI.start(primaryStage);
    }

    /**
     * Opens the Market screen.
     */
    public void goToMarket() {
        market = new Market(this); // market refreshes every time you go
        MarketScreen marketUI = new MarketScreen(this);
        marketUI.start(primaryStage);
    }

    /**
     * Opens the Inventory screen.
     */
    public void openInventory() {
        InventoryScreen inventoryUI = new InventoryScreen(this);
        inventoryUI.start(primaryStage);
    }

    /**
     * Random rain event.
     */
    public void rain() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Rain Occurring!");
        alert.setHeaderText("Rain Occurring!");
        alert.setContentText("Water levels of all crops are about to increase by 0-2 levels, "
                + "possibly killing some!");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);

        Random rand = new Random();
        for (Crop crop : getFarm().getCrops()) {
            if (!crop.getType().equals("") && !crop.getIsDead()) {
                int num = rand.nextInt(3) + 1;
                crop.setWaterLevel(crop.getWaterLevel() + num);
                if (crop.getWaterLevel() > 3) {
                    crop.setIsDead(true);
                    crop.setDeadBoi(true);
                }
            }
        }
    }

    /**
     * Random drought event.
     */
    public void drought() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Drought Occurring!");
        alert.setHeaderText("Drought Occurring!");
        alert.setContentText("Water levels of all crops are about to decrease by 1-3 levels, "
                + "possibly killing some!");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);

        Random rand = new Random();
        for (Crop crop : getFarm().getCrops()) {
            if (!crop.getType().equals("") && !crop.getIsDead()) {
                int num = rand.nextInt(3);
                crop.setWaterLevel(crop.getWaterLevel() - num);
                if (crop.getWaterLevel() < 0) {
                    crop.setIsDead(true);
                    crop.setDeadBoi(true);
                }
            }
        }
    }

    /**
     * Random locusts event.
     */
    public void locusts() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Locusts Occurring!");
        alert.setHeaderText("Locusts Occurring!");
        alert.setContentText("Locusts are about to attack your crops, killing about "
                + "half of them!");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);

        Random rand = new Random();
        for (Crop crop : getFarm().getCrops()) {
            if (!crop.getType().equals("") && !crop.getIsDead()) {
                int num = rand.nextInt(2);
                if (num == 0) {
                    crop.setIsDead(true);
                    crop.setDeadBoi(true);
                }
            }
        }
    }

    /**
     * Manipulates the data necessary when harvest is called.
     * @param cropIndex The passed in crop index.
     */
    public void harvest(int cropIndex) {
        Crop harvested = farm.harvest(cropIndex);
        if (harvested != null) {
            inventory.add(harvested);
        } else if (inventory.getAmount() > 19) {
            Alert tooFull = new Alert(Alert.AlertType.ERROR, "Inventory space full!");
            tooFull.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        } else {
            Alert tooYoung = new Alert(Alert.AlertType.ERROR, "Cannot harvest yet");
            tooYoung.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
    }

    /**
     * Manipulates the necessary data when plant is called and refreshes.
     * @param cropIndex the index of the crop passed in.
     */
    public void plant(int cropIndex) {
        PlantingScreen screen = new PlantingScreen(this, cropIndex);
        screen.start(primaryStage);
    }

    /**
     * Waters crop at given crop index
     * @param cropIndex int index
     */
    public void water(int cropIndex) {
        farm.getCrops().get(cropIndex).water();
    }

    /**
     * Getter for player's inventory.
     * @return the player's inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Getter for player.
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter for farm.
     * @return the farm
     */
    public Farm getFarm() {
        return farm;
    }

    /**
     * Getter for market.
     * @return the market
     */
    public Market getMarket() {
        return market;
    }

    /**
     * Getter for universal stage.
     * @param primaryStage the universal stage.
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Getter for the day.
     * @return the day.
     */
    public int getDay() {
        return day;
    }

    /**
     * Setter for the day.
     * @param day The new day.
     */
    public void setDay(int day) {
        this.day = day;
    }
}