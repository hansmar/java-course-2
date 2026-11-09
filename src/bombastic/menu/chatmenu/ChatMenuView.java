package bombastic.menu.chatmenu;

import bombastic.Model;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChatMenuView extends BorderPane {

    private final ListView<String> userList = new ListView<>();
    private final TextArea messageArea = new TextArea();
    private final TextArea inputArea = new TextArea();
    private final Button sendButton = new Button("Send");
    private final Button mainMenuButton = new Button("Main menu");

    public ChatMenuView(Model model, ChatModel chatModel) {

        userList.itemsProperty().bind(chatModel.usersProperty());
        VBox usersBox = new VBox(5, createTitle("Online users"), userList);
        VBox.setVgrow(userList, Priority.ALWAYS);

        messageArea.setEditable(false);
        messageArea.setWrapText(true);
        messageArea.textProperty().bind(chatModel.messagesProperty());
        VBox messagesBox = new VBox(5, createTitle("Messages"), messageArea);
        VBox.setVgrow(messageArea, Priority.ALWAYS);

        HBox topRow = new HBox(10, usersBox, messagesBox);
        HBox.setHgrow(messagesBox, Priority.ALWAYS);
        // topRow.setStyle("-fx-border-color: red; -fx-border-width: 2;");

        inputArea.setPromptText("Enter message here...");
        inputArea.setWrapText(true);
        inputArea.setPrefRowCount(4);
        VBox buttons = new VBox(10, sendButton, mainMenuButton);
        HBox bottomRow = new HBox(10, inputArea, buttons);
        HBox.setHgrow(inputArea, Priority.ALWAYS);

        setCenter(topRow);
        setBottom(bottomRow);
        BorderPane.setMargin(bottomRow, new Insets(10, 0, 0, 0));
        setPadding(new Insets(15));
        backgroundProperty().bind(model.backgroundProperty());
    }

    private Label createTitle(String text) {
        Label title = new Label(text);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        return title;
    }

    public TextArea getInputArea() {
        return inputArea;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}
