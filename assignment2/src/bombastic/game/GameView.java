package bombastic.game;

import bombastic.Model;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class GameView extends BorderPane {

    private final Label arenaLabel = new Label();
    private final Button mainMenuButton = new Button("Main menu");

    public GameView(Model model) {
        arenaLabel.textProperty().bind(Bindings.concat("Game is loading. Arena: ", model.arenaProperty()));
        arenaLabel.getStyleClass().add("game-text");
        setCenter(arenaLabel);
        setBottom(mainMenuButton);
        BorderPane.setMargin(mainMenuButton, new Insets(10));
        getStyleClass().add("game-view");
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}
