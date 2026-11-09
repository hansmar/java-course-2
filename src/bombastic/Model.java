package bombastic;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Model {

    public static final List<String> ARENAS = List.of("Classic", "Desert", "Ice");

    private final ObjectProperty<Background> background = new SimpleObjectProperty<>();
    private final StringProperty arena = new SimpleStringProperty("Classic");   // default arena

    public Model() {
        setBackgroundImage("bg1.png");
        // setBackgroundImage("bg2.png");
        // background.addListener((obs, oldValue, newValue) -> System.out.println("Background changed"));
        // arena.addListener((obs, oldValue, newValue) -> System.out.println("Arena: " + oldValue + " -> " + newValue));
    }

    public ObjectProperty<Background> backgroundProperty() {
        return background;
    }

    public StringProperty arenaProperty() {
        return arena;
    }

    public void setBackgroundImage(String fileName) {
        // System.out.println("Loading: " + getClass().getResource("/images/" + fileName));
        Image image = new Image(getClass().getResource("/images/" + fileName).toExternalForm());
        BackgroundSize size = new BackgroundSize(
                BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        background.set(new Background(backgroundImage));
    }
}
