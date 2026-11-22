package bombastic.game;

import java.util.List;
import bombastic.Controller;
import bombastic.Model;
import bombastic.game.entities.Entity;
import javafx.animation.AnimationTimer;

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
            gameModel.getPlayer1().keyPressed(code);
            gameModel.getPlayer2().keyPressed(code);
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

                view.render(gameModel.getEntities());
            }
        };
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
