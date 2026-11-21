package bombastic.menu.settingsmenu;

import bombastic.Model;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class SettingsMenuView extends VBox {

    private final MenuButton backgroundMenu = new MenuButton("Change background");
    private final MenuItem background1Item = new MenuItem("Background 1");
    private final MenuItem background2Item = new MenuItem("Background 2");
    private final MenuItem background3Item = new MenuItem("Background 3");
    private final MenuButton arenaMenu = new MenuButton("Choose arena");
    private final Label arenaLabel = new Label();
    private final Button mainMenuButton = new Button("Main menu");

    public SettingsMenuView(Model model) {
        super(10);
        setAlignment(Pos.CENTER);

        backgroundMenu.setPrefWidth(200);
        arenaMenu.setPrefWidth(200);

        backgroundMenu.getItems().addAll(background1Item, background2Item, background3Item);
        for (MenuItem item : backgroundMenu.getItems()) {
            item.setStyle("-fx-pref-width: 184px;");
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

    public MenuItem getBackground1Item() {
        return background1Item;
    }

    public MenuItem getBackground2Item() {
        return background2Item;
    }

    public MenuItem getBackground3Item() {
        return background3Item;
    }

    public MenuButton getArenaMenu() {
        return arenaMenu;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }
}
