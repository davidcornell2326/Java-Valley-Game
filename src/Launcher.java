import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launcher Class
 * @author David Cornell
 * @version 1.0
 */
public class Launcher extends Application {
    /**
     * Main method instantiates Launcher, creates primaryStage, and calls start
     * with primaryStage.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Instantiates controller and gives it primaryStage (necessary in order to
     * create and use a universal stage).
     * @param primaryStage the universal stage
     */
    public void start(Stage primaryStage) {
        Controller controller = new Controller(primaryStage);
    }
}