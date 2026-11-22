package bombastic.game.arena;

import java.util.ArrayList;
import java.util.List;

import bombastic.game.GameSettings;
import bombastic.game.entities.Crate;
import bombastic.game.entities.Entity;
import bombastic.game.entities.Floor;
import bombastic.game.entities.Wall;
import javafx.scene.image.Image;

public class Arena {

    private static final char[][] MAP = {
        "###############".toCharArray(),
        "#1.cccccccccc.#".toCharArray(),
        "#.#c#c#c#c#c#c#".toCharArray(),
        "#ccccccccccccc#".toCharArray(),
        "#c#c#c#c#c#c#c#".toCharArray(),
        "#ccccccccccccc#".toCharArray(),
        "#c#c#c#c#c#c#c#".toCharArray(),
        "#ccccccccccccc#".toCharArray(),
        "#c#c#c#c#c#c#.#".toCharArray(),
        "#.cccccccccc.2#".toCharArray(),
        "###############".toCharArray()
    };

    private final Image grassImage;
    private final Image wallImage;
    private final Image crateImage;

    public Arena() {
        grassImage = new Image(getClass().getResource("/images/terrain/grass.png").toExternalForm());
        wallImage = new Image(getClass().getResource("/images/terrain/wall.png").toExternalForm());
        crateImage = new Image(getClass().getResource("/images/terrain/crate.png").toExternalForm());
    }

    public List<Entity> createEntities() {
        List<Entity> entities = new ArrayList<>();
        int size = GameSettings.TILE_SIZE;

        for (int row = 0; row < MAP.length; row++) {
            for (int col = 0; col < MAP[row].length; col++) {
                double x = col * size;
                double y = row * size;
                char tile = MAP[row][col];

                entities.add(new Floor(x, y, size, size, grassImage));

                if (tile == '#') {
                    entities.add(new Wall(x, y, size, size, wallImage));
                } else if (tile == 'c') {
                    entities.add(new Crate(x, y, size, size, crateImage));
                }
            }
        }
        return entities;
    }
}