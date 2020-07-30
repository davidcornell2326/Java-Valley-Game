import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

/**
 * @author David, Kevin, Nathan, and Rachel
 * @version 1.0
 *
 * Planting GUI
 */
public class PlantingScreen extends Application {
    private Controller controller;
    private Stage primaryStage;
    private List<Crop> inventoryOfCrops;
    private int cropIndex;

    /**
     * Assigns the instance variables controller and cropIndex.
     * @param controller The controller passed in.
     * @param cropIndex The index of a specific crop.
     */
    public PlantingScreen(Controller controller, int cropIndex) {
        this.controller = controller;
        this.inventoryOfCrops = controller.getInventory().getInventoryOfCrops();
        this.cropIndex = cropIndex;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Plant a Seed");
        BorderPane root = new BorderPane();
        root.setTop(getTop());
        root.setCenter(getCenter());
        root.setBottom(getBottom());

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Gets the top of the screen set up with the instructions.
     * @return A StackPane for the top of the screen.
     */
    private StackPane getTop() {
        Label instructions = new Label("Choose a seed from your inventory to plant");
        StackPane pane = new StackPane();
        pane.getChildren().add(instructions);
        return pane;
    }

    /**
     * Displays the available seeds in inventory and a plant button.
     * @return A ScrollPane for the center of the screen.
     */
    private ScrollPane getCenter() {
        VBox inventory = new VBox(5);
        int inventoryIndex = 0;
        for (Crop crop : inventoryOfCrops) {
            final int inventoryIndexCopy = inventoryIndex;
            HBox cropBox = new HBox(15);
            if (crop.getAge() == 0) { // Only seeds will be shown
                String cropType = crop.getType() + " (Seeds)";
                Label cropLabel = new Label(cropType);
                Button plantButton = new Button("Plant");
                plantButton.setOnAction(e -> {
                    controller.getFarm().plant(crop.getType(), cropIndex, inventoryIndexCopy);
                    controller.goToFarm();
                });
                cropBox.getChildren().addAll(cropLabel, plantButton);
            }
            inventory.getChildren().add(cropBox);
            inventoryIndex++;
        }

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(inventory);
        scroll.fitToWidthProperty().set(true);
        return scroll;
    }

    /**
     * Displays the return button at the bottom.
     * @return A StackPane for the returning button.
     */
    private StackPane getBottom() {
        StackPane pane = new StackPane();
        Button cancelButton = new Button("Cancel (Return to Farm)");
        cancelButton.setOnAction(e -> {
            controller.goToFarm();
        });
        pane.getChildren().add(cancelButton);
        return pane;
    }
}
