package bombastic.game.entities;

import bombastic.game.GameSettings;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Player extends Entity {

    private int lives = 3;
    private int maxBombs = 1;
    private int range = 1;
    private double speed = 0.15;
    private int activeBombs = 0;
    private double invulnerableTime = 0;

    private final KeyCode upKey;
    private final KeyCode downKey;
    private final KeyCode leftKey;
    private final KeyCode rightKey;
    private final KeyCode bombKey;

    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;

    private double previousX;
    private double previousY;

    public Player(double x, double y, double size, Image image,
                  KeyCode upKey, KeyCode downKey, KeyCode leftKey, KeyCode rightKey, KeyCode bombKey) {
        super(x, y, size, size, image);
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.bombKey = bombKey;
    }

    public void keyPressed(KeyCode code) {
        setKey(code, true);
    }

    public void keyReleased(KeyCode code) {
        setKey(code, false);
    }

    private void setKey(KeyCode code, boolean pressed) {
        if (code == upKey) {
            movingUp = pressed;
        } else if (code == downKey) {
            movingDown = pressed;
        } else if (code == leftKey) {
            movingLeft = pressed;
        } else if (code == rightKey) {
            movingRight = pressed;
        }
    }

    @Override
    public void update(double dt) {
        previousX = x;
        previousY = y;

        if (invulnerableTime > 0) {
            invulnerableTime -= dt;
        }

        double distance = speed * dt;
        if (movingUp) {
            y -= distance;
        }
        if (movingDown) {
            y += distance;
        }
        if (movingLeft) {
            x -= distance;
        }
        if (movingRight) {
            x += distance;
        }
    }

    @Override
    public boolean isOccupying() {
        return false;
    }

    @Override
    public void interact(Entity other) {
        if (other.isOccupying()) {
            x = previousX;
            y = previousY;
        } else if (other instanceof Explosion) {
            takeHit();
        }
    }

    public Bomb placeBomb() {
        if (activeBombs >= maxBombs) {
            return null;
        }
        int size = GameSettings.TILE_SIZE;
        int col = (int) ((x + width / 2) / size);
        int row = (int) ((y + height / 2) / size);
        activeBombs++;
        return new Bomb(col * size, row * size, size, this);
    }

    public void bombExploded() {
        activeBombs--;
    }

    public KeyCode getBombKey() {
        return bombKey;
    }

    public int getRange() {
        return range;
    }

    private void takeHit() {
        if (invulnerableTime <= 0) {
            lives--;
            invulnerableTime = 1000;
            System.out.println("Player hit, lives left: " + lives);
        }
    }

    public void increaseMaxBombs() {
        maxBombs++;
    }

    public void increaseRange() {
        range++;
    }

    public void increaseSpeed() {
        speed += 0.03;
    }

    public void addLife() {
        lives++;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public int getLives() {
        return lives;
    }
}