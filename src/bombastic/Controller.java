package bombastic;

import bombastic.game.GameController;
import bombastic.menu.chatmenu.ChatMenuController;
import bombastic.menu.mainmenu.MainMenuController;
import bombastic.menu.settingsmenu.SettingsMenuController;

public class Controller {

    private final Model model = new Model();
    private final View view = new View();

    private final MainMenuController mainMenuController;
    private final SettingsMenuController settingsMenuController;
    private final ChatMenuController chatMenuController;
    private final GameController gameController;

    public Controller() {
        mainMenuController = new MainMenuController(model, this);
        settingsMenuController = new SettingsMenuController(model, this);
        chatMenuController = new ChatMenuController(model, this);
        gameController = new GameController(model, this);
        // System.out.println("All four sub-controllers created");
        showMainMenu();
    }

    public void showMainMenu() {
        view.show(mainMenuController.getView());
    }

    public void showSettingsMenu() {
        view.show(settingsMenuController.getView());
    }

    public void showChatMenu() {
        view.show(chatMenuController.getView());
    }

    public void showGame() {
        // System.out.println("Start game, arena is: " + model.arenaProperty().get());
        view.show(gameController.getView());
    }

    public View getView() {
        return view;
    }
}
