import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Market GUI
 * @author David, Kevin, Nathan, and Rachel
 * @version 1.0
 */
public class MarketScreen extends Application {
    private Controller controller;
    private Stage primaryStage;
    private int amount = 0;

    /**
     * Constructor taking the main controller object.
     * @param controller the controller object
     */
    public MarketScreen(Controller controller) {
        this.controller = controller;
    }

    /**
     * Market screen constructor for the launch to work.
     */
    public MarketScreen() { } // no-arg constructor for launch to work

    /**
     * Creates and shows the screen.
     * @param primaryStage the universal stage
     */
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Market Place");
        BorderPane root = new BorderPane();
        root.setLeft(getLeft());
        root.setCenter(getCenter());
        root.setBottom(getBottom());
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Gets the inventory data to display.
     * @return The left VBox that contains inventory data.
     */
    private VBox getInventory() {
        VBox left = new VBox();
        left.setStyle("-fx-border-color: black");
        left.setPadding(new Insets(10));
        Label label = new Label("Current inventory:");
        left.getChildren().add(label);
        amount = 0;
        for (int i = 0; i < controller.getInventory().getInventoryOfCrops().size(); i++) {
            final int itemIndex = i;
            amount++;
            Crop crop = controller.getInventory().getInventoryOfCrops().get(i);
            Image image = new Image(crop.getImageLink());
            final ImageView cropView = new ImageView(image);
            cropView.setPreserveRatio(true);
            cropView.setFitHeight(140);
            cropView.setFitWidth(140);
            cropView.setStyle("-fx-border-color: black");
            left.getChildren().addAll(cropView);

            //if (crop.getAge() == 3) {
            Button sellButton = new Button("Sell");
            sellButton.setOnAction(e -> {
                controller.getInventory().sell(itemIndex);
                start(primaryStage);
            });
            sellButton.setPrefSize(60, 20);
            int sellPrice =
                    controller.getInventory().getInventoryOfCrops().get(itemIndex).getSellPrice();
            Label x = new Label(crop.getType() + ": $" + sellPrice);
            left.getChildren().add(x);
            left.getChildren().add(sellButton);
            //} else {
            //    Label x = new Label(crop.getType() + " (seed)");
            //    left.getChildren().add(x);
            //}
            Separator separator = new Separator();
            separator.setMaxWidth(200);
            separator.setPadding(new Insets(10));
            left.getChildren().addAll(separator);

        }
        Label count = new Label(amount + " of 20 full");
        count.setStyle("-fx-font-size: 16");
        left.getChildren().add(count);
        return left;
    }

    /**
     * Gets the information for the center of the market.
     * @return The right VBox that contains necessary data.
     */
    private VBox getInfo() {
        VBox right = new VBox();
        right.setStyle("-fx-border-color: black");
        right.setPadding(new Insets(10));
        Label label = new Label("Welcome to the Market!!");
        label.setStyle("-fx-font-size: 18");
        right.getChildren().add(label);
        int l = controller.getMarket().getItemsForSale().size();
        for (int i = 0; i < l; i++) {
            final int itemIndex = i;
            MarketItem item = controller.getMarket().getItemsForSale().get(i);
            String crop = item.getSeedType();
            Image image;
            if (crop.toLowerCase().equals("wheat")) {
                image = new Image("https://imgur.com/cIWrANF.png");
            } else if (crop.toLowerCase().equals("carrots")) {
                image = new Image("https://imgur.com/wgSD1YK.png");
            } else {
                image = new Image("https://imgur.com/EJSc9GM.png");
            }
            ImageView cropView = new ImageView(image);
            cropView.setPreserveRatio(true);
            cropView.setFitHeight(100);
            cropView.setFitWidth(100);
            cropView.setStyle("-fx-border-color: black");
            Label ll = new Label(item.getSeedType() + " (seed) for $" + item.getPrice());
            Button buyButton = new Button("Buy");
            buyButton.setOnAction(e -> {
                controller.getMarket().buy(itemIndex);
                start(primaryStage);
            });
            buyButton.setPrefSize(60, 20);
            right.getChildren().addAll(cropView, buyButton, ll);
            Separator separator = new Separator();
            separator.setMaxWidth(200);
            separator.setPadding(new Insets(10));
            right.getChildren().add(separator);
        }
        return right;
    }

    /**
     * Gets the center of market to be displayed.
     * @return A ScrollPane that contains market data.
     */
    private ScrollPane getCenter() {
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(getInfo());
        scroll.fitToWidthProperty().set(true);
        return scroll;
    }

    /**
     * Gets the data for the left side with inventory data.
     * @return A ScrollPane that contains inventory data.
     */
    private ScrollPane getLeft() {
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(getInventory());
        scroll.fitToWidthProperty().set(true);
        return scroll;
    }

    /**
     * Gets an image url and height and returns it as a Pane.
     * @param url The url of the image.
     * @param height The desired height of the image.
     * @return A Pane that contains the image data.
     */
    private Pane getImage(String url, double height) {
        Image image = new Image(url);
        ImageView i = new ImageView(image);
        i.setFitHeight(height);
        i.setFitWidth(height);
        Pane p = new Pane(i);
        i.setStyle("-fx-border-color: black");
        p.setPadding(new Insets(0, 5, 0, 0));
        return p;
    }

    /**
     * Calls the methods to format the bottom of the screen.
     * @return A BorderPane that contains the return button and money.
     */
    private BorderPane getBottom() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #008000");
        pane.setPadding(new Insets(10));
        pane.setLeft(getBottomLeft());
        pane.setRight(getBottomRight());
        return pane;
    }

    /**
     * Gets the data for the left side like the return button.
     * @return A VBox that contains the return button.
     */
    private VBox getBottomLeft() {
        VBox b = new VBox();
        Button farmButton = new Button("Return to Farm");
        farmButton.setOnAction(e -> {
            controller.goToFarm();
        });
        b.getChildren().addAll(farmButton);
        return b;
    }

    /**
     * Gets the data for the right side with current money.
     * @return A VBox that contains the money label.
     */
    private VBox getBottomRight() {
        VBox b = new VBox();
        int m = controller.getPlayer().getMoney();
        Label money = new Label("Current Money: $" + String.valueOf(m));
        money.setStyle("-fx-font-size: 18");
        b.getChildren().addAll(money);
        return b;
    }
}