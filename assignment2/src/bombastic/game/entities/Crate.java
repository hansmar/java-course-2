package bombastic.game.entities;

import javafx.scene.image.Image;

public class Crate extends Entity {

    public Crate(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public boolean isOccupying() {
        return true;
    }

    @Override
    public void interact(Entity other) {
        if (other instanceof Explosion) {
            remove();
        }
    }
}