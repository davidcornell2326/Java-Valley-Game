import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;

import java.util.Random;

/**
 * Farm GUI
 * @author Rachel, Nathan, David, and Kevin
 * @version 1.1
 */
public class Main extends Application {
    private Controller controller;
    private int difficulty;
    private GridPane plots;
    private Stage primaryStage;

    /**
     * Constructor taking the main controller object.
     * @param controller the controller object
     */
    public Main(Controller controller) {
        this.controller = controller;
    }

    /**
     * Farm screen constructor for the launch to work.
     */
    public Main() { } // no-arg constructor for launch to work

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        BorderPane pane = new BorderPane();
        pane.setPrefSize(700, 700);
        pane.setBottom(getBottom());
        pane.setRight(getRight());
        pane.setCenter(getCenter());
        pane.setTop(getTop());
        ScrollPane scrollPane = new ScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        primaryStage.setTitle("Initial Farm");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Returns a VBox that's for the upper left with date.
     * @return A VBox with the date.
     */
    private HBox getTop() {
        HBox top = new HBox(10);
        top.setPadding(new Insets(5));
        top.setStyle("-fx-background-color: #008000");
        Label day = new Label("Day " + controller.getDay());
        day.setStyle("-fx-font-size: 18");

        Button increaseDay = new Button("+");
        increaseDay.setOnAction(e -> {
            controller.setDay(controller.getDay() + 1);
            for (Crop curr: controller.getFarm().getCrops()) {
                if (curr.getWaterLevel() == 0 || curr.getAge() == 3) {
                    curr.setIsDead(true);
                    curr.setDeadBoi(true);
                } else if (!curr.getType().equals("")) {
                    curr.grow();
                    curr.setWaterLevel(curr.getWaterLevel() - 1);
                }
                // Else case not present because it is the case for empty plots; do nothing
            }

            // Random events:
            Random rand = new Random();
            int bound;
            int diff = controller.getPlayer().getDifficulty();
            if (diff == 0) {
                bound = 15;
            } else if (diff == 1) {
                bound = 10;
            } else {
                bound = 5;
            }
            int num = rand.nextInt(bound);
            if (num == 0) {
                controller.rain();
            } else if (num == 1) {
                controller.drought();
            } else if (num == 2) {
                controller.locusts();
            }

            start(primaryStage);
        });
        top.getChildren().addAll(day, increaseDay);
        return top;
    }

    /** Returns a BorderPane that splits up the bottom into a left & right side.
     * @return A BorderPane for the bottom.
     */
    private BorderPane getBottom() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #008000");
        pane.setLeft(getBottomLeft());
        pane.setRight(getBottomRight());
        return pane;
    }

    /** Returns a VBox that has money displayed & inventory.
     * @return A VBox for the bottom left.
     */
    private VBox getBottomLeft() {
        VBox left = new VBox();
        left.setPadding(new Insets(10));
        double money = controller.getPlayer().getMoney();
        Label moneyLabel = new Label("Current Money: $" + String.format("%.2f", money));
        moneyLabel.setStyle("-fx-font-size: 18");
        moneyLabel.setPadding(new Insets(4, 4, 4, 0));

        Button inventoryButton = new Button("Inventory");
        inventoryButton.setOnAction(e -> {
            controller.openInventory();
        });
        //inventoryButton.setStyle("-fx-background-color: green");
        left.getChildren().addAll(moneyLabel, inventoryButton);
        return left;
    }

    /** Returns a VBox that displays the market button.
     * @return A VBox for the bottom right.
     */
    private VBox getBottomRight() {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #008000");
        //Image image = new Image("https://imgur.com/Nt8g2wS.png");
        Button marketButton = new Button("Go to Market");
        marketButton.setOnAction(e -> {
            controller.goToMarket();
        });
        //marketButton.setGraphic(new ImageView(image));
        //marketButton.setMaxHeight(20);
        //marketButton.setMaxWidth(20);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(marketButton);
        vbox.setSpacing(10);
        return vbox;
    }

    /** Returns a GridPane that displays the plots.
     * @return A GridPane for the center of the screen.
     */
    private GridPane getCenter() {
        plots = new GridPane();
        plots.setStyle("-fx-background-color: #008000");
        plots.setPadding(new Insets(10));
        plots.setHgap(10);
        plots.setVgap(10);
        plots.setAlignment(Pos.CENTER);
        int cropIndex = -1;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                cropIndex++; // corresponds to the index of the crops arraylist
                final int cropIndexCopy = cropIndex;
                StackPane plot = new StackPane();
                Rectangle plotShape = new Rectangle(130, 130);
                plotShape.setArcHeight(5);
                plotShape.setArcWidth(5);
                plotShape.setStyle("-fx-fill: SaddleBrown");
                plot.getChildren().add(plotShape);
                VBox plotContent = new VBox(3);
                plotContent.setAlignment(Pos.CENTER);
                Crop currentCrop = controller.getFarm().getCrops().get(cropIndex);
                currentCrop.updateImageLink();
                Image cropImage = new Image(currentCrop.getImageLink());
                final ImageView cropView = new ImageView(cropImage);
                cropView.setPreserveRatio(true);
                cropView.setFitHeight(60);
                plotContent.getChildren().add(cropView);

                // water progress bar
                ProgressBar wetness = new ProgressBar(currentCrop.getWaterLevel() / 3.0);
                Label waterLabel = new Label("Water: " + currentCrop.getWaterLevel() + "/3");
                if (currentCrop.getWaterLevel() == 3) {
                    wetness.setStyle("-fx-accent: red");
                } else if (currentCrop.getWaterLevel() == 1) {
                    wetness.setStyle("-fx-accent: orange");
                }

                // Water button
                Button waterButton = new Button("Water");
                waterButton.setOnAction(e -> {
                    if (currentCrop.getWaterLevel() == 3) {
                        currentCrop.setIsDead(true);
                        currentCrop.setDeadBoi(true);
                    } else {
                        controller.water(cropIndexCopy); // this increases the crop's water
                        // level by 1 (nothing else)
                        wetness.setProgress(currentCrop.getWaterLevel() / 3.0);
                        waterLabel.setText("Water: " + currentCrop.getWaterLevel() + "/3");
                        if (currentCrop.getWaterLevel() == 3) {
                            wetness.setStyle("-fx-accent: red");
                        } else if (currentCrop.getWaterLevel() == 1) {
                            wetness.setStyle("-fx-accent: orange");
                        }
                        // Determination of whether to kill plant is done in increment day button
                        currentCrop.updateImageLink();
                    }
                    start(primaryStage);
                });
                waterButton.setPrefSize(80, 20);

                // Plant button
                // TO-DO: put functionality in entity classes, not GUIs
                Button plantButton = new Button("Plant");
                plantButton.setOnAction(e -> {
                    controller.plant(cropIndexCopy);
                    cropView.setImage(new Image(
                            controller.getFarm().getCrops().get(cropIndexCopy).getImageLink()));
                });
                plantButton.setPrefSize(80, 20);

                // Harvest button
                // TO-DO: put functionality in entity classes, not GUIs
                Button harvestButton = new Button("Harvest");
                harvestButton.setOnAction(e -> {
                    controller.harvest(cropIndexCopy);
                    cropView.setImage(new Image(
                            controller.getFarm().getCrops().get(cropIndexCopy).getImageLink()));
                    plotContent.getChildren().clear();
                    plotContent.getChildren().addAll(cropView, plantButton);
                });
                harvestButton.setPrefSize(80, 20);

                // Clear button
                Button clearButton = new Button("Clear");
                clearButton.setOnAction(e -> {
                    currentCrop.setType("");
                    currentCrop.setIsDead(false);
                    currentCrop.setDeadBoi(false);
                    currentCrop.setAge(0);
                    currentCrop.updateImageLink();
                    currentCrop.setWaterLevel(-1);
                    cropView.setImage(new Image(
                            controller.getFarm().getCrops().get(cropIndexCopy).getImageLink()));
                    plotContent.getChildren().clear();
                    plotContent.getChildren().addAll(cropView, plantButton);
                });
                clearButton.setPrefSize(80, 20);

                if (currentCrop.getIsDead()) {
                    plotContent.getChildren().add(clearButton);
                } else if (currentCrop.getType().equals("")) {
                    plotContent.getChildren().add(plantButton);
                } else if (currentCrop.getAge() == 3) {
                    plotContent.getChildren().add(harvestButton);
                } else {
                    plotContent.getChildren().addAll(waterButton, wetness, waterLabel);
                }
                plot.getChildren().add(plotContent);
                plots.add(plot, r, c);
            }
        }
        return plots;
    }

    /** Returns a BorderPane that displays the farm.
     * @return A BorderPane for the right side of the screen.
     */
    private BorderPane getRight() {
        BorderPane right = new BorderPane();
        right.setRight(getImage("https://imgur.com/6UcJGj8.png", 150));
        right.setStyle("-fx-background-color: #008000");
        return right;
    }

    /** Returns a Pane that displays an image for a URL.
     * @param url the image url
     * @param height the desired image height
     * @return A Pane that formats an image properly.
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

    /** Returns a String that has difficulty player chose.
     * @return A String after getting the player's difficulty.
     */
    private int getDifficulty() {
        return difficulty;
    }

    /**
     * Public setter method for difficulty
     * @param difficulty 0 - easy, 1 - medium, 2 - hard
     */
    public void setDifficulty(int difficulty) {
        if (difficulty < 0 || difficulty > 2) {
            throw new IllegalArgumentException("Invalid difficulty");
        }
        this.difficulty = difficulty;
    }

    /**
     * Executes the main execution of the program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}