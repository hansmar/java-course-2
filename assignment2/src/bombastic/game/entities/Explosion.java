package bombastic.game.entities;

import javafx.scene.image.Image;

public class Explosion extends Entity {

    private static final Image IMAGE = new Image(Explosion.class.getResource("/images/explosions/explosion_3.png").toExternalForm());

    private double timeLeft = 500;

    public Explosion(double x, double y, double size) {
        super(x, y, size, size, IMAGE);
    }

    @Override
    public void update(double dt) {
        timeLeft -= dt;
        if (timeLeft <= 0) {
            remove();
        }
    }

    @Override
    public boolean isOccupying() {
        return false;
    }

    @Override
    public void interact(Entity other) {
    }
}