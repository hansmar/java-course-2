package bombastic.game.arena;

import java.util.ArrayList;
import java.util.List;

import bombastic.game.GameSettings;
import bombastic.game.entities.Crate;
import bombastic.game.entities.Entity;
import bombastic.game.entities.Floor;
import bombastic.game.entities.Player;
import bombastic.game.entities.Wall;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

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
    private final Image player1Image;
    private final Image player2Image;

    private Player player1;
    private Player player2;

    public Arena() {
        grassImage = new Image(getClass().getResource("/images/terrain/grass.png").toExternalForm());
        wallImage = new Image(getClass().getResource("/images/terrain/wall.png").toExternalForm());
        crateImage = new Image(getClass().getResource("/images/terrain/crate.png").toExternalForm());
        player1Image = new Image(getClass().getResource("/images/players/red_down_0.png").toExternalForm());
        player2Image = new Image(getClass().getResource("/images/players/blue_down_0.png").toExternalForm());
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
                } else if (tile == '1') {
                    double playerSize = size * 0.75;
                    double offset = (size - playerSize) / 2;
                    player1 = new Player(x + offset, y + offset, playerSize, player1Image,
                            KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D, KeyCode.SPACE);
                } else if (tile == '2') {
                    double playerSize = size * 0.75;
                    double offset = (size - playerSize) / 2;
                    player2 = new Player(x + offset, y + offset, playerSize, player2Image,
                            KeyCode.I, KeyCode.K, KeyCode.J, KeyCode.L, KeyCode.ENTER);
                }
            }
        }

        entities.add(player1);
        entities.add(player2);

        return entities;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
