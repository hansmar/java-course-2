package bombastic.game;

import bombastic.Controller;
import bombastic.Model;

public class GameController {

    private final GameModel gameModel = new GameModel();
    private final GameView view;

    public GameController(Model model, Controller parent) {
        view = new GameView(model);
        view.getMainMenuButton().setOnAction(e -> parent.showMainMenu());
        view.render(gameModel.getEntities());
    }

    public GameView getView() {
        return view;
    }
}