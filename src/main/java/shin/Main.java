package shin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The entry point for the Shin chatbot application.
 * This class initializes and launches the JavaFX graphical user interface.
 */
public class Main extends Application {
    private Shin shin = new Shin("data/tasks.txt");

    /**
     * Starts the JavaFX application by setting up the main stage and loading the GUI.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // ✅ Set Product Name in Title Bar
            stage.setTitle("Shin - Your Smart Task Assistant");

            fxmlLoader.<MainWindow>getController().setShin(new Shin());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
