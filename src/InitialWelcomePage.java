import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Welcome Page GUI
 * @author hcho367
 * @version 11.0
 */
public class InitialWelcomePage extends Application {
    private Stage primaryStage;
    private Controller controller;

    /**
     * Constructor taking the main controller object.
     * @param controller the controller object
     */
    public InitialWelcomePage(Controller controller) {
        this.controller = controller;
    }

    /**
     * Welcome screen constructor for the launch to work.
     */
    public InitialWelcomePage() { } // no-arg constructor for launch to work

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(650);
        BorderPane borderpane = new BorderPane();
        Image image = new Image("https://vignette.wikia.nocookie.net/gow-fireage/"
                + "images/8/8b/Farm22.png/revision/latest?cb=20160324042948");
        // create a background image
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        // create Background
        Background background = new Background(backgroundimage);
        borderpane.setBackground(background);

        //adding labels and stuff
        Label welcomeLabel = new Label("Welcome to Farmville!");
        welcomeLabel.setFont(new Font("Arial", 30));
        welcomeLabel.setTranslateY(30);
        welcomeLabel.setTextFill(Color.web("#BCE09E"));

        welcomeLabel.setBorder(new Border(new BorderStroke(Color.GREENYELLOW,
                BorderStrokeStyle.SOLID, null, new BorderWidths(5))));

        Button welcomeButton = new Button("Click to Start");
        welcomeButton.setOnAction(e -> {
            controller.startPlayerConfiguration();
        });

        borderpane.setCenter(welcomeLabel);
        borderpane.setBottom(welcomeButton);

        welcomeButton.setPadding(new Insets(20, 20, 20, 20)); // makes the button bigger
        BorderPane.setMargin(welcomeButton, new Insets(10, 10, 200, 10)); //moves the button upwards
        BorderPane.setAlignment(welcomeButton, Pos.CENTER);


        // Create a scene and place it in the stage
        Scene scene = new Scene(borderpane, 300, 100);
        primaryStage.setTitle("Welcome Page"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     * @param args arg from command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}