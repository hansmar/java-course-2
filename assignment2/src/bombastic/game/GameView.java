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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class GameView extends StackPane {

    private final Button mainMenuButton = new Button("Main menu");
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

        getChildren().addAll(canvas, mainMenuButton);
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

    public void setOnMainMenu(Runnable action) {
        mainMenuButton.setOnAction(e -> action.run());
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
