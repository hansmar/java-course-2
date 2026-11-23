package bombastic.game.entities;

import javafx.scene.image.Image;

public abstract class PowerUp extends Entity {

    public PowerUp(double x, double y, double size, Image image) {
        super(x, y, size, size, image);
    }

    protected abstract void apply(Player player);

    @Override
    public boolean isOccupying() {
        return false;
    }

    @Override
    public void interact(Entity other) {
        if (other instanceof Player && !isRemoved()) {
            apply((Player) other);
            remove();
        }
    }
}