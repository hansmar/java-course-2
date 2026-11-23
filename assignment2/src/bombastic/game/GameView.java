package bombastic.game;

import java.util.List;
import java.util.function.Consumer;

import bombastic.Model;
import bombastic.game.entities.Entity;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameView extends StackPane {

    private final Button mainMenuButton = new Button("Main menu");
    private final Label livesLabel = new Label();
    private final Label resultLabel = new Label();
    private final Button newRoundButton = new Button("New round");
    private final Button resultMainMenuButton = new Button("Main menu");
    private final VBox resultBox = new VBox(15, resultLabel, newRoundButton, resultMainMenuButton);
    private final Canvas canvas;
    private final GraphicsContext gc;

    public GameView(Model model) {
        canvas = new Canvas(
            GameSettings.COLUMNS * GameSettings.TILE_SIZE,
            GameSettings.ROWS * GameSettings.TILE_SIZE
        );
        canvas.setFocusTraversable(true);
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        livesLabel.getStyleClass().add("game-text");
        StackPane.setAlignment(livesLabel, Pos.TOP_CENTER);
        StackPane.setMargin(livesLabel, new Insets(6));

        resultLabel.getStyleClass().add("result-text");
        newRoundButton.getStyleClass().add("menu-choice");
        resultMainMenuButton.getStyleClass().add("menu-choice");
        resultBox.setAlignment(Pos.CENTER);
        resultBox.getStyleClass().add("result-box");
        resultBox.setVisible(false);

        getChildren().addAll(canvas, livesLabel, mainMenuButton, resultBox);
        StackPane.setAlignment(mainMenuButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(mainMenuButton, new Insets(10));
        getStyleClass().add("game-view");
    }

    public void render(List<Entity> entities) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity entity : entities) {
            entity.render(gc);
        }
    }

    public void showLives(int redLives, int blueLives) {
        livesLabel.setText("Red: " + redLives + " lives        Blue: " + blueLives + " lives");
    }

    public void showResult(String text) {
        resultLabel.setText(text);
        resultBox.setVisible(true);
    }

    public void hideResult() {
        resultBox.setVisible(false);
    }

    public void setOnMainMenu(Runnable action) {
        mainMenuButton.setOnAction(e -> action.run());
        resultMainMenuButton.setOnAction(e -> action.run());
    }

    public void setOnNewRound(Runnable action) {
        newRoundButton.setOnAction(e -> action.run());
    }

    public void setOnKeyDown(Consumer<KeyCode> action) {
        canvas.setOnKeyPressed(e -> action.accept(e.getCode()));
    }

    public void setOnKeyUp(Consumer<KeyCode> action) {
        canvas.setOnKeyReleased(e -> action.accept(e.getCode()));
    }

    public void requestGameFocus() {
        canvas.requestFocus();
    }
}