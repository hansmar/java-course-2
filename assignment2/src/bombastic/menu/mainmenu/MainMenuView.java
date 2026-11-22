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
        startButton.getStyleClass().add("menu-choice");
        chatButton.getStyleClass().add("menu-choice");
        settingsButton.getStyleClass().add("menu-choice");
        exitButton.getStyleClass().add("menu-choice");
        getChildren().addAll(startButton, chatButton, settingsButton, exitButton);
        backgroundProperty().bind(model.backgroundProperty());
        // getStyleClass().add("test-border");
    }

    public void setOnStartGame(Runnable action) {
        startButton.setOnAction(e -> action.run());
    }

    public void setOnChat(Runnable action) {
        chatButton.setOnAction(e -> action.run());
    }

    public void setOnSettings(Runnable action) {
        settingsButton.setOnAction(e -> action.run());
    }

    public void setOnExit(Runnable action) {
        exitButton.setOnAction(e -> action.run());
    }
}
