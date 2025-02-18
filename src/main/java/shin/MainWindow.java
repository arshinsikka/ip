package shin;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import shin.Shin;

public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Shin shin;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));


    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Ensure Send button works with a mouse click
        sendButton.setOnMouseClicked(event -> handleUserInput());

        // Ensure ENTER key works to send messages
        userInput.setOnAction(event -> handleUserInput());
    }


    public void setShin(Shin s) {
        shin = s;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();  // Trim to remove extra spaces
        if (input.isEmpty()) { // Prevent empty messages
            return;
        }

        String response = shin.getResponse(input);

        // Ensure UI updates correctly
        Platform.runLater(() -> {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            userInput.clear(); // Clear input field after sending
        });

        // Ensure ScrollPane moves down to show latest messages
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

}
