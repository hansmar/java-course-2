package bombastic.game;

import bombastic.Controller;
import bombastic.Model;


public class GameController {

    private final GameView view;

    public GameController(Model model, Controller parent) {
        view = new GameView(model);
        view.getMainMenuButton().setOnAction(e -> parent.showMainMenu());
    }

    public GameView getView() {
        return view;
    }
}
