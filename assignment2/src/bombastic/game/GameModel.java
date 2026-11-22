package bombastic.game;

import java.util.List;
import java.util.ArrayList;

import bombastic.game.arena.Arena;
import bombastic.game.entities.Entity;
import bombastic.game.entities.Player;

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
        List<Entity[]> collisions = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                Entity a = entities.get(i);
                Entity b = entities.get(j);
                if (CollisionDetection.aabb(a, b)) {
                        collisions.add(new Entity[] {a, b});
                }
            }
        }
        return collisions;
    }

    public void processCollisions(List<Entity[]> collisions) {
       for (Entity[] pair : collisions) {
            System.out.println(pair[0].getClass().getSimpleName() + " hits " + pair[1].getClass().getSimpleName());
            pair[0].interact(pair[1]);
            pair[1].interact(pair[0]);
        }
    }

    public void addEntity(Entity entity) {
        newEntities.add(entity);
    }

    public void applyChanges() {
        entities.addAll(entities.indexOf(player1), newEntities);
        newEntities.clear();
        entities.removeIf(entity -> entity.isRemoved());
    }
}