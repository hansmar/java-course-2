package bombastic.game;

import java.util.List;
import java.util.ArrayList;
import bombastic.game.arena.Arena;
import bombastic.game.entities.Entity;
import bombastic.game.entities.Player;
import bombastic.game.entities.PowerUp;
import bombastic.game.entities.RangePowerUp;
import bombastic.game.entities.SpeedPowerUp;
import bombastic.game.entities.Bomb;
import bombastic.game.entities.CapacityPowerUp;
import bombastic.game.entities.Crate;
import bombastic.game.entities.Explosion;
import bombastic.game.entities.LifePowerUp;
import bombastic.game.entities.Wall;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class GameModel {

    private final List<Entity> entities;
    private final Player player1;
    private final Player player2;
    private final List<Entity> newEntities = new ArrayList<>();

    public GameModel() {
        Arena arena = new Arena();
        entities = arena.createEntities();
        player1 = arena.getPlayer1();
        player2 = arena.getPlayer2();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<Entity[]> findCollisions() {
        return findCollisionsFrom(0, 1);
    }

    public List<Entity[]> findCollisionsConcurrently(ExecutorService executor, int threads) {
        List<Callable<List<Entity[]>>> tasks = new ArrayList<>();
        for (int t = 0; t < threads; t++) {
            int start = t;
            tasks.add(() -> findCollisionsFrom(start, threads));
        }

        List<Entity[]> collisions = new ArrayList<>();
        try {
            for (Future<List<Entity[]>> future : executor.invokeAll(tasks)) {
                collisions.addAll(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return collisions;
    }

    private List<Entity[]> findCollisionsFrom(int start, int step) {
        List<Entity[]> found = new ArrayList<>();
        for (int i = start; i < entities.size(); i += step) {
            for (int j = i + 1; j < entities.size(); j++) {
                Entity a = entities.get(i);
                Entity b = entities.get(j);
                if (CollisionDetection.aabb(a, b)) {
                    found.add(new Entity[] {a, b});
                }
            }
        }
        return found;
    }

    public void processCollisions(List<Entity[]> collisions) {
       for (Entity[] pair : collisions) {
            // System.out.println(pair[0].getClass().getSimpleName() + " hits " + pair[1].getClass().getSimpleName());
            pair[0].interact(pair[1]);
            pair[1].interact(pair[0]);
        }
    }

    public void addEntity(Entity entity) {
        newEntities.add(entity);
    }

    public void applyChanges() {

        for (Entity entity : entities) {
            if (entity instanceof Bomb) {
                Bomb bomb = (Bomb) entity;
                if (bomb.isExploding()) {
                    explode(bomb);
                }
            } else if (entity instanceof Crate && entity.isRemoved()) {
                PowerUp powerUp = createRandomPowerUp(entity.getPositionX(), entity.getPositionY());
                if (powerUp != null) {
                    addEntity(powerUp);
                }
            }
        }

        entities.addAll(entities.indexOf(player1), newEntities);
        newEntities.clear();
        entities.removeIf(entity -> entity.isRemoved());
    }

    private PowerUp createRandomPowerUp(double x, double y) {
        if (Math.random() >= 0.3) {
            return null;
        }
        double size = GameSettings.TILE_SIZE;
        double type = Math.random();
        if (type < 0.25) {
            return new CapacityPowerUp(x, y, size);
        } else if (type < 0.5) {
            return new RangePowerUp(x, y, size);
        } else if (type < 0.75) {
            return new SpeedPowerUp(x, y, size);
        } else {
            return new LifePowerUp(x, y, size);
        }
    }

    private void explode(Bomb bomb) {
        bomb.finishExplosion();
        double size = GameSettings.TILE_SIZE;
        addEntity(new Explosion(bomb.getPositionX(), bomb.getPositionY(), size));
        spread(bomb, 1, 0);
        spread(bomb, -1, 0);
        spread(bomb, 0, 1);
        spread(bomb, 0, -1);
    }

    private void spread(Bomb bomb, int dx, int dy) {
        double size = GameSettings.TILE_SIZE;
        for (int i = 1; i <= bomb.getRange(); i++) {
            double x = bomb.getPositionX() + dx * i * size;
            double y = bomb.getPositionY() + dy * i * size;
            Entity obstacle = findObstacleAt(x, y);
            if (obstacle instanceof Wall) {
                return;
            }
            addEntity(new Explosion(x, y, size));
            if (obstacle instanceof Crate) {
                return;
            }
        }
    }

    private Entity findObstacleAt(double x, double y) {
        for (Entity entity : entities) {
            if ((entity instanceof Wall || entity instanceof Crate)
                    && entity.getPositionX() == x && entity.getPositionY() == y) {
                return entity;
            }
        }
        return null;
    }
}