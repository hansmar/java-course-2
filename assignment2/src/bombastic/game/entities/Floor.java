package bombastic.game.entities;

import javafx.scene.image.Image;

public class Floor extends Entity {

    public Floor(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    @Override
    public boolean isOccupying() {
        return false;
    }

    @Override
    public void interact(Entity other) {
        // Empty: players can walk freely on floor tiles
    }
}
