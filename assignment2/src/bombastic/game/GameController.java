package bombastic.game;

import java.util.List;
import bombastic.Controller;
import bombastic.Model;
import bombastic.game.entities.Bomb;
import bombastic.game.entities.Entity;
import bombastic.game.entities.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

public class GameController {

    private final GameModel gameModel = new GameModel();
    private final GameView view;
    private final AnimationTimer gameLoop;

    public GameController(Model model, Controller parent) {
        view = new GameView(model);
        view.setOnMainMenu(() -> {
            stop();
            parent.showMainMenu();
        });
        view.setOnKeyDown(code -> {
            keyPressed(gameModel.getPlayer1(), code);
            keyPressed(gameModel.getPlayer2(), code);
        });
        view.setOnKeyUp(code -> {
            gameModel.getPlayer1().keyReleased(code);
            gameModel.getPlayer2().keyReleased(code);
        });

        gameLoop = new AnimationTimer() {
            long lastTime = -1;

            @Override
            public void handle(long currentTime) {
                if (lastTime == -1) {
                    lastTime = currentTime;
                }
                double dt = (currentTime - lastTime) / 1_000_000.0;
                lastTime = currentTime;
                // System.out.println("dt: " + dt);


                for (Entity entity : gameModel.getEntities()) {
                    entity.update(dt);
                }

                List<Entity[]> collisions = gameModel.findCollisions();
                gameModel.processCollisions(collisions);

                gameModel.applyChanges();

                view.render(gameModel.getEntities());
            }
        };
    }

    private void keyPressed(Player player, KeyCode code) {
        player.keyPressed(code);
        if (code == player.getBombKey()) {
            Bomb bomb = player.placeBomb();
            if (bomb != null) {
                gameModel.addEntity(bomb);
            }
        }
    }

    public void start() {
        gameLoop.start();
        view.requestGameFocus();
    }

    public void stop() {
        gameLoop.stop();
    }

    public GameView getView() {
        return view;
    }
}
