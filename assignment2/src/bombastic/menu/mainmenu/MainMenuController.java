package bombastic.menu.mainmenu;

import bombastic.Controller;
import bombastic.Model;
import javafx.application.Platform;

public class MainMenuController {

    private final MainMenuView view;

    public MainMenuController(Model model, Controller parent) {
        view = new MainMenuView(model);
        view.setOnStartGame(() -> parent.showGame());
        view.setOnChat(() -> parent.showChatMenu());
        view.setOnSettings(() -> parent.showSettingsMenu());
        view.setOnExit(() -> Platform.exit());
    }

    public MainMenuView getView() {
        return view;
    }
}
