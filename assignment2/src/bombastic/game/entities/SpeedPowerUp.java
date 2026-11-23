package bombastic.game.entities;

import javafx.scene.image.Image;

public class SpeedPowerUp extends PowerUp {

    private static final Image IMAGE = new Image(SpeedPowerUp.class.getResource("/images/powerups/speed.png").toExternalForm());

    public SpeedPowerUp(double x, double y, double size) {
        super(x, y, size, IMAGE);
    }

    @Override
    protected void apply(Player player) {
        player.increaseSpeed();
    }
}