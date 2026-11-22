package bombastic.game.entities;

import javafx.scene.image.Image;

public class Bomb extends Entity {

    private static final Image IMAGE = new Image(Bomb.class.getResource("/images/bombs/bomb_0.png").toExternalForm());

    private final Player owner;
    private final int range;
    private double fuseTime = 2000;
    private boolean solid = false;
    private boolean playerOnTop = true;
    private boolean exploding = false;

    public Bomb(double x, double y, double size, Player owner) {
        super(x, y, size, size, IMAGE);
        this.owner = owner;
        this.range = owner.getRange();
    }

    @Override
    public void update(double dt) {
        if (!playerOnTop) {
            solid = true;
        }
        playerOnTop = false;

        fuseTime -= dt;
        if (fuseTime <= 0) {
            exploding = true;
        }
    }

    public boolean isExploding() {
        return exploding && !isRemoved();
    }

    public void finishExplosion() {
        remove();
        owner.bombExploded();
    }

    public int getRange() {
        return range;
    }

    @Override
    public boolean isOccupying() {
        return solid;
    }

    @Override
    public void interact(Entity other) {
        if (other instanceof Player) {
            playerOnTop = true;
        } else if (other instanceof Explosion) {
            exploding = true;
        }
    }
}