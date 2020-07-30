import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Player Configuration GUI
 * @author David Cornell
 * @version 1.0
 */
public class PlayerConfigurationScreen extends Application {
    private Controller controller;
    private String name;
    private int difficulty = -1;
    private String startingSeed;
    private Stage primaryStage;

    /**
     * Assigns the instance variable controller to the parameter.
     * @param controller The controller passed in.
     */
    public PlayerConfigurationScreen(Controller controller) {
        this.controller = controller;
    }

    /**
     * Main method to launch the stage.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        BorderPane root = new BorderPane();
        root.setTop(this.getTitle());
        root.setCenter(this.getConfigurations());
        root.setBottom(this.getStartLine());
        primaryStage.setTitle("Player Configuration");
        Scene scene = new Scene(root);
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the top title bar.
     * @return A StackPane to be the top title bar.
     */
    private StackPane getTitle() {
        StackPane title = new StackPane();
        title.setPadding(new Insets(10));
        Label label = new Label("Configure Player");
        label.setStyle("-fx-font-size: 20");
        title.setStyle("-fx-background-color: gray");
        title.getChildren().add(label);
        return title;
    }

    /**
     * Creates the center configuration options.
     * @return a GridPane containing the configuration options
     */
    private GridPane getConfigurations() {
        GridPane configs = new GridPane();
        //configs.setGridLinesVisible(true);
        configs.setStyle("-fx-background-color: lightblue");
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(200);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(600);
        configs.setPrefSize(850, 550);
        configs.getColumnConstraints().addAll(column1, column2);
        configs.setVgap(100);
        configs.setPadding(new Insets(10));

        Label enterNameLabel = new Label("Enter PlayerName:");
        Label chooseDifficultyLabel = new Label("Choose Difficulty:");
        Label selectSeedLabel = new Label("Select Starting Seed:");
        GridPane.setValignment(enterNameLabel, VPos.TOP);
        GridPane.setValignment(chooseDifficultyLabel, VPos.TOP);
        GridPane.setValignment(selectSeedLabel, VPos.TOP);
        VBox enterName = new VBox(5);
        VBox chooseDifficulty = new VBox(5);
        VBox selectSeed = new VBox(5);
        configs.add(enterNameLabel, 0, 0);
        configs.add(chooseDifficultyLabel, 0, 1);
        configs.add(selectSeedLabel, 0, 2);
        configs.add(enterName, 1, 0);
        configs.add(chooseDifficulty, 1, 1);
        configs.add(selectSeed, 1, 2);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter name here");
        Label nameDesc = new Label("Up to 12 alphabetical characters and spaces");
        Label nameStatus = new Label();
        Button nameButton = new Button("Enter");
        nameButton.setOnAction(e -> {
            if (validName(nameField.getText())) {
                name = nameField.getText();
                nameStatus.setText("That name is valid!");
                nameStatus.setStyle("-fx-text-fill: green");
                nameField.setStyle("-fx-text-fill: green");
            } else {
                name = null;
                nameStatus.setText("That name is invalid, please try again");
                nameStatus.setStyle("-fx-text-fill: red");
                nameField.setStyle("-fx-text-fill: red");
            }
        });
        HBox nameBox = new HBox(10);
        nameBox.getChildren().addAll(nameField, nameButton);
        enterName.getChildren().addAll(nameBox, nameDesc, nameStatus);

        ComboBox difficultyOptions = new ComboBox();
        difficultyOptions.getItems().addAll("Easy", "Medium", "Hard");
        difficultyOptions.setPromptText("Choose difficulty");
        Label difficultyDesc = new Label();
        difficultyOptions.setOnAction(e -> {
            if (difficultyOptions.getValue().equals("Easy")) {
                difficultyDesc.setText("Crops are cheap, disasters are minimal,"
                        + " and everything is just nice");
                difficulty = 0;
            } else if (difficultyOptions.getValue().equals("Medium")) {
                difficultyDesc.setText("Crops are more expensive, disasters occur occasionally, "
                        + "and everything is a good, hearty challenge");
                difficulty = 1;
            } else {
                difficultyDesc.setText("Crops are very expensive, disasters occur frequently, "
                        + "and everything is misery. Good luck!");
                difficulty = 2;
            }
        });
        chooseDifficulty.getChildren().addAll(difficultyOptions, difficultyDesc);

        ComboBox seedOptions = new ComboBox();
        seedOptions.getItems().addAll("Wheat", "Carrots", "Potatoes");
        seedOptions.setPromptText("Choose seed type");
        Label seedDesc = new Label();
        seedOptions.setOnAction(e -> {
            if (seedOptions.getValue().equals("Wheat")) {
                seedDesc.setText("I sure hope you plan on turning it into bread before eating it");
                startingSeed = "Wheat";
            } else if (seedOptions.getValue().equals("Carrots")) {
                seedDesc.setText("Legend says if you eat enough you get x-ray vision");
                startingSeed = "Carrots";
            } else {
                seedDesc.setText("Baked potatoes, french fries, sweet potatoes... "
                        + "wait no that's not right");
                startingSeed = "Potatoes";
            }
        });
        selectSeed.getChildren().addAll(seedOptions, seedDesc);
        return configs;
    }

    /**
     * Creates the bottom information and start game line.
     * @return a BorderPane containing information and the start game button.
     */
    private BorderPane getStartLine() {
        BorderPane startLine = new BorderPane();
        Label info = new Label("Java Valley Copyright 2020 by C++ Students");
        info.setTextAlignment(TextAlignment.LEFT);
        info.setPadding(new Insets(10));
        Button startButton = new Button("Start Game");
        startButton.setPadding(new Insets(10));
        startButton.setOnAction(e -> {
            if (name != null && difficulty != -1 && startingSeed != null) {
                controller.setUpGame(name, difficulty, startingSeed);
                controller.goToFarm();
            }
        });
        startLine.setLeft(info);
        startLine.setRight(startButton);
        startLine.setStyle("-fx-background-color: lightgray");
        return startLine;
    }

    /**
     * Validates the name the user enters.
     * @param nameAttempt the player's entered name (to be validated).
     * @return true if the name is valid, false otherwise.
     */
    private boolean validName(String nameAttempt) {
        String validCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        char[] nameAttemptArray = nameAttempt.toCharArray();
        for (char let : nameAttemptArray) {
            String letter = String.valueOf(let);
            if (!validCharacters.contains(letter)) {
                return false;
            }
        }
        if (nameAttempt.length() < 1 || nameAttempt.length() > 12) {
            return false;
        }
        return true;
    }
}