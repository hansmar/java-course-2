package bombastic.game;

import java.util.List;

import bombastic.game.arena.Arena;
import bombastic.game.entities.Entity;

public class GameModel {

    private final List<Entity> entities;

    public GameModel() {
        entities = new Arena().createEntities();
    }

    public List<Entity> getEntities() {
        return entities;
    }
}