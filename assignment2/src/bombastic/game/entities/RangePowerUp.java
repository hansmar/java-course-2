package bombastic.game.entities;

import javafx.scene.image.Image;

public class RangePowerUp extends PowerUp {

    private static final Image IMAGE = new Image(RangePowerUp.class.getResource("/images/powerups/range.png").toExternalForm());

    public RangePowerUp(double x, double y, double size) {
        super(x, y, size, IMAGE);
    }

    @Override
    protected void apply(Player player) {
        player.increaseRange();
    }
}