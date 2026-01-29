package hal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Hal hal;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image halImage = new Image(this.getClass().getResourceAsStream("/images/DaHal.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Hal instance */
    public void setHal(Hal d) {
        hal = d;
        greet();
    }

    /**
     * Displays the welcome message when the application starts.
     */
    private void greet() {
        String welcomeMessage = hal.getWelcomeMessage();
        dialogContainer.getChildren().add(
                DialogBox.getHalDialog(welcomeMessage, halImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Hal's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = hal.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getHalDialog(response, halImage)
        );
        userInput.clear();
        
        // Close the application after displaying goodbye message
        if (input.trim().equalsIgnoreCase("bye")) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
            // Delay closing to allow user to see the goodbye message
            new Thread(() -> {
                try {
                    Thread.sleep(1500); // 1.5 second delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.exit();
            }).start();
        }
    }
}
