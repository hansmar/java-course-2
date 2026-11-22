package bombastic.game;

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
        view.getMainMenuButton().setOnAction(e -> {
            stop();
            parent.showMainMenu();
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
                System.out.println("dt: " + dt);

                for (Entity entity : gameModel.getEntities()) {
                    entity.update(dt);
                }

                view.render(gameModel.getEntities());
            }
        };
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }

    public GameView getView() {
        return view;
    }
}