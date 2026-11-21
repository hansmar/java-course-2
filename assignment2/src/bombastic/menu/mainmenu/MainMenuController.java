package bombastic.menu.mainmenu;

import bombastic.Controller;
import bombastic.Model;
import javafx.application.Platform;

public class MainMenuController {

    private final MainMenuView view;

    public MainMenuController(Model model, Controller parent) {
        view = new MainMenuView(model);
        view.getStartButton().setOnAction(e -> parent.showGame());
        view.getChatButton().setOnAction(e -> parent.showChatMenu());
        view.getSettingsButton().setOnAction(e -> parent.showSettingsMenu());
        view.getExitButton().setOnAction(e -> Platform.exit());
    }

    public MainMenuView getView() {
        return view;
    }
}
