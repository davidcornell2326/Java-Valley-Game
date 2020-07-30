import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Inventory GUI
 * @author David, Kevin, Nathan, and Rachel
 * @version 1.0
 */
public class InventoryScreen extends Application {
    private Controller controller;

    /**
     * Constructor taking the main controller object.
     * @param controller the controller object
     */
    public InventoryScreen(Controller controller) {
        this.controller = controller;
    }

    /**
     * Inventory screen constructor for the launch to work.
     */
    public InventoryScreen() { } // no-arg constructor for launch to work

    /**
     * Main method to instantiate and launch with stage.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates and shows the screen.
     * @param primaryStage the universal stage
     */
    public void start(Stage primaryStage) {
        Pane root = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);
        Button farmButton = new Button("Return to Farm");
        farmButton.setOnAction(e -> {
            controller.goToFarm();
        });

        int i = 1;
        for (Crop crop: controller.getInventory().getInventoryOfCrops()) {
            Label label;
            String cropName = crop.getType();
            if (crop.getAge() == 0) {
                label = new Label(i + ") Seed: " + cropName);
                label.setTextFill(Color.web("#ff4d4d"));
            } else {
                label = new Label(i + ") Harvested: " + cropName);
                label.setTextFill(Color.web("#BCE09E"));
            }
            label.setFont(Font.font("Arial", 32));
            root.getChildren().add(label);
            i++;
        }

        root.getChildren().addAll(farmButton);
        Scene scene = new Scene(scrollPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inventory");
        primaryStage.show();
    }
}