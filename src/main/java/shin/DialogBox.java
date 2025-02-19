package shin;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box in the chat interface, containing a text message and an image.
 * This class is responsible for rendering user and chatbot messages in the GUI.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a {@code DialogBox} with the given text and image.
     *
     * @param text The message to be displayed in the dialog box.
     * @param img The image to be displayed alongside the text.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Creates a dialog box for user messages.
     *
     * @param text The text message entered by the user.
     * @param img The user's avatar image.
     * @return A new {@code DialogBox} instance representing the user's message.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }



    /**
     * Creates a dialog box for chatbot (Duke/Shin) messages.
     * This dialog box is flipped horizontally to differentiate from user messages.
     *
     * @param text The chatbot's response text.
     * @param img The chatbot's avatar image.
     * @return A new {@code DialogBox} instance representing the chatbot's response.
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }


    /**
     * Flips the dialog box so that the image appears on the left and text on the right.
     * Used for chatbot messages to distinguish them from user messages.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

}
