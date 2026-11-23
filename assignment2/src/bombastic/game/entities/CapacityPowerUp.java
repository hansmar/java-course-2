package bombastic.game.entities;

import javafx.scene.image.Image;

public class CapacityPowerUp extends PowerUp {

    private static final Image IMAGE = new Image(CapacityPowerUp.class.getResource("/images/powerups/capacity.png").toExternalForm());

    public CapacityPowerUp(double x, double y, double size) {
        super(x, y, size, IMAGE);
    }

    @Override
    protected void apply(Player player) {
        player.increaseMaxBombs();
    }
}