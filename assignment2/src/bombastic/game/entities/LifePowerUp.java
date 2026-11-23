package bombastic.game.entities;

import javafx.scene.image.Image;

public class LifePowerUp extends PowerUp {

    private static final Image IMAGE = new Image(LifePowerUp.class.getResource("/images/powerups/life.png").toExternalForm());

    public LifePowerUp(double x, double y, double size) {
        super(x, y, size, IMAGE);
    }

    @Override
    protected void apply(Player player) {
        player.addLife();
    }
}