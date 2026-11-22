package bombastic.game;

import java.util.List;

import bombastic.game.arena.Arena;
import bombastic.game.entities.Entity;
import bombastic.game.entities.Player;

public class GameModel {

    private final List<Entity> entities;
    private final Player player1;
    private final Player player2;

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
}