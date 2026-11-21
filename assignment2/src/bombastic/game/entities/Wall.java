package bombastic.game.entities;

import javafx.scene.image.Image;

public class Wall extends Entity {

    public Wall(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public boolean isOccupying() {
        return true;
    }

    @Override
    public void interact(Entity other) {
        // Empty: walls are indestructible and block movement
    }
}
