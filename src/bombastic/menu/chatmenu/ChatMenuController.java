package bombastic.menu.chatmenu;

import bombastic.Controller;
import bombastic.Model;


public class ChatMenuController {

    private final ChatModel chatModel = new ChatModel();
    private final ChatMenuView view;

    public ChatMenuController(Model model, Controller parent) {
        view = new ChatMenuView(model, chatModel);
        view.getSendButton().setOnAction(e -> sendMessage());
        view.getMainMenuButton().setOnAction(e -> parent.showMainMenu());
        // chatModel.usersProperty().add("Bomberman");
    }

    private void sendMessage() {
        // System.out.println("Input was: [" + view.getInputArea().getText() + "]");
        String text = view.getInputArea().getText().trim();
        if (!text.isEmpty()) {
            chatModel.addMessage("You: " + text);
            view.getInputArea().clear();
        }
    }

    public ChatMenuView getView() {
        return view;
    }
}
