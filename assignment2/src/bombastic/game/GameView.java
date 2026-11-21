package bombastic.game;


import bombastic.game.entities.Crate;
import bombastic.game.entities.Wall;
import bombastic.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class GameView extends StackPane {

    private final Button mainMenuButton = new Button("Main menu");
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Image grassImage;

    public GameView(Model model) {

        canvas = new Canvas(
            GameSettings.COLUMNS * GameSettings.TILE_SIZE,
            GameSettings.ROWS * GameSettings.TILE_SIZE
        );

        gc = canvas.getGraphicsContext2D();

        grassImage = new Image(getClass().getResource("/images/terrain/grass.png").toExternalForm());

        gc.setImageSmoothing(false);

        getChildren().addAll(canvas, mainMenuButton);
        StackPane.setAlignment(mainMenuButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(mainMenuButton, new Insets(10));

        drawArena();

        Image wallImage = new Image(getClass().getResource("/images/terrain/wall.png").toExternalForm());
        Image crateImage = new Image(getClass().getResource("/images/terrain/crate.png").toExternalForm());

        int size = GameSettings.TILE_SIZE;
        Wall wall = new Wall(0, 0, size, size, wallImage);
        Crate crate = new Crate(1 * size, 0, size, size, crateImage);

        wall.render(gc);
        crate.render(gc);

        getStyleClass().add("game-view");
    }

    private void drawArena() {
        for (int row = 0; row < GameSettings.ROWS; row++) {
            for (int col = 0; col < GameSettings.COLUMNS; col++) {
                gc.drawImage(
                    grassImage,
                    col * GameSettings.TILE_SIZE,
                    row * GameSettings.TILE_SIZE,
                    GameSettings.TILE_SIZE,
                    GameSettings.TILE_SIZE
                );
            }
        }
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}