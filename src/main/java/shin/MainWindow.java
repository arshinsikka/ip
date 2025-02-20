package shin;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI of the chatbot.
 * This class handles user input, manages the display of dialog boxes, and interacts with the chatbot logic.
 */
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


    /**
     * Initializes the GUI components.
     * Binds the scroll pane to automatically scroll to the latest message.
     * Adds event listeners for user input via button click or pressing Enter.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Ensure Send button works with a mouse click
        sendButton.setOnMouseClicked(event -> handleUserInput());

        // Ensure ENTER key works to send messages
        userInput.setOnAction(event -> handleUserInput());
    }

    /**
     * Sets the chatbot instance that will handle user inputs.
     *
     * @param s The chatbot instance (Shin) to be used for processing responses.
     */
    public void setShin(Shin s) {
        this.shin = s;
    }

    /**
     * Handles user input from the text field.
     * Sends the input to the chatbot, retrieves a response, and displays both messages in dialog boxes.
     * Also ensures the scroll pane moves to show the latest messages.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        System.out.println("DEBUG: User input -> " + input); // ✅ Debugging output
        String response = shin.getResponse(input);
        System.out.println("DEBUG: Shin response -> " + response); // ✅ Debugging output

        Platform.runLater(() -> {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage)
            );
            userInput.clear();
        });

        // ✅ Ensure ScrollPane moves to show the latest messages
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        // ✅ Force UI refresh after the response is displayed
        dialogContainer.requestLayout();
    }


}
