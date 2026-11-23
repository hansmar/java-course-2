package bombastic.game;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bombastic.Controller;
import bombastic.Model;
import bombastic.game.entities.Bomb;
import bombastic.game.entities.Entity;
import bombastic.game.entities.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

public class GameController {

    private GameModel gameModel = new GameModel();
    private final GameView view;
    private final AnimationTimer gameLoop;
    private final ExecutorService executor = Executors.newFixedThreadPool(GameSettings.COLLISION_THREADS);
    private long lastTime = -1;

    public GameController(Model model, Controller parent) {
        view = new GameView(model);
        view.setOnMainMenu(() -> {
            stop();
            parent.showMainMenu();
        });
        view.setOnNewRound(() -> startNewRound());
        view.setOnKeyDown(code -> {
            keyPressed(gameModel.getPlayer1(), code);
            keyPressed(gameModel.getPlayer2(), code);
        });
        view.setOnKeyUp(code -> {
            gameModel.getPlayer1().keyReleased(code);
            gameModel.getPlayer2().keyReleased(code);
        });

        gameLoop = new AnimationTimer() {
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

                // long startTime = System.nanoTime();
                List<Entity[]> collisions = gameModel.findCollisionsConcurrently(executor, GameSettings.COLLISION_THREADS);
                // List<Entity[]> collisions = gameModel.findCollisions();
                // System.out.println("Collision detection: " + (System.nanoTime() - startTime) / 1000 + " microseconds");
                gameModel.processCollisions(collisions);

                gameModel.applyChanges();

                view.render(gameModel.getEntities());
                view.showLives(gameModel.getPlayer1().getLives(), gameModel.getPlayer2().getLives());

                String result = gameModel.getResult();
                if (result != null) {
                    stop();
                    view.showResult(result);
                }
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

    public void startNewRound() {
        gameModel = new GameModel();
        view.hideResult();
        start();
    }

    public void start() {
        lastTime = -1;
        gameLoop.start();
        view.requestGameFocus();
    }

    public void stop() {
        gameLoop.stop();
    }

    public void shutdown() {
        gameLoop.stop();
        executor.shutdown();
    }

    public GameView getView() {
        return view;
    }
}