package bombastic.menu.settingsmenu;

import bombastic.Controller;
import bombastic.Model;
import javafx.scene.control.MenuItem;

public class SettingsMenuController {

    private final SettingsMenuView view;

    public SettingsMenuController(Model model, Controller parent) {
        view = new SettingsMenuView(model);

        view.getBackground1Item().setOnAction(e -> model.setBackgroundImage("bg1.png"));
        view.getBackground2Item().setOnAction(e -> model.setBackgroundImage("bg2.png"));
        view.getBackground3Item().setOnAction(e -> model.setBackgroundImage("bg3.png"));
        // view.setBackground(null);

        for (MenuItem item : view.getArenaMenu().getItems()) {
            // System.out.println("Handler added for: " + item.getText());
            item.setOnAction(e -> model.arenaProperty().set(item.getText()));
        }

        view.getMainMenuButton().setOnAction(e -> parent.showMainMenu());
    }

    public SettingsMenuView getView() {
        return view;
    }
}
