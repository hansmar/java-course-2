package bombastic.menu.settingsmenu;

import bombastic.Model;
import java.util.function.Consumer;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class SettingsMenuView extends VBox {

    private final MenuButton backgroundMenu = new MenuButton("Change background");
    private final MenuButton arenaMenu = new MenuButton("Choose arena");
    private final Label arenaLabel = new Label();
    private final Button mainMenuButton = new Button("Main menu");

    public SettingsMenuView(Model model) {
        super(10);
        setAlignment(Pos.CENTER);

        backgroundMenu.setPrefWidth(200);
        arenaMenu.setPrefWidth(200);

        for (int i = 0; i < Model.BACKGROUNDS.size(); i++) {
            MenuItem item = new MenuItem("Background " + (i + 1));
            item.setStyle("-fx-pref-width: 184px;");
            backgroundMenu.getItems().add(item);
        }

        for (String arena : Model.ARENAS) {
            MenuItem item = new MenuItem(arena);
            item.setStyle("-fx-pref-width: 184px;");
            arenaMenu.getItems().add(item);
        }

        arenaLabel.textProperty().bind(Bindings.concat("Current arena: ", model.arenaProperty()));
        arenaLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        getChildren().addAll(backgroundMenu, arenaMenu, mainMenuButton, arenaLabel);
        backgroundProperty().bind(model.backgroundProperty());
        // System.out.println("Background menu items: " + backgroundMenu.getItems().size());
        // System.out.println("Arena menu items: " + arenaMenu.getItems().size());
    }

    public void setOnBackgroundChosen(Consumer<String> action) {
        for (int i = 0; i < Model.BACKGROUNDS.size(); i++) {
            String fileName = Model.BACKGROUNDS.get(i);
            backgroundMenu.getItems().get(i).setOnAction(e -> action.accept(fileName));
        }
    }

    public void setOnArenaChosen(Consumer<String> action) {
        for (MenuItem item : arenaMenu.getItems()) {
            // System.out.println("Handler added for: " + item.getText());
            item.setOnAction(e -> action.accept(item.getText()));
        }
    }

    public void setOnMainMenu(Runnable action) {
        mainMenuButton.setOnAction(e -> action.run());
    }
}
