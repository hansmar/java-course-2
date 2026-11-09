package bombastic.menu.mainmenu;

import bombastic.Model;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuView extends VBox {

    private final Button startButton = new Button("Start game");
    private final Button chatButton = new Button("Chat");
    private final Button settingsButton = new Button("Settings");
    private final Button exitButton = new Button("Exit");

    public MainMenuView(Model model) {
        super(10);
        setAlignment(Pos.CENTER);
        startButton.setPrefWidth(200);
        chatButton.setPrefWidth(200);
        settingsButton.setPrefWidth(200);
        exitButton.setPrefWidth(200);
        getChildren().addAll(startButton, chatButton, settingsButton, exitButton);
        backgroundProperty().bind(model.backgroundProperty());
        // setStyle("-fx-border-color: red; -fx-border-width: 3;");
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getChatButton() {
        return chatButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
