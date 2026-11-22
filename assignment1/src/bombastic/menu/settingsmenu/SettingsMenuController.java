package bombastic.menu.settingsmenu;

import bombastic.Controller;
import bombastic.Model;

public class SettingsMenuController {

    private final SettingsMenuView view;

    public SettingsMenuController(Model model, Controller parent) {
        view = new SettingsMenuView(model);

        view.setOnBackgroundChosen(fileName -> model.setBackgroundImage(fileName));
        view.setOnArenaChosen(arena -> model.arenaProperty().set(arena));
        view.setOnMainMenu(() -> parent.showMainMenu());
        // model.setBackgroundImage("bg3.png");
    }

    public SettingsMenuView getView() {
        return view;
    }
}
