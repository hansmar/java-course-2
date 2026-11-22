package bombastic.game;

import java.util.List;

import bombastic.Model;
import bombastic.game.entities.Entity;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        canvas.setFocusTraversable(true);

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

    public Button getMainMenuButton() {
        return mainMenuButton;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}