package bombastic.menu.chatmenu;

import bombastic.Controller;
import bombastic.Model;

public class ChatMenuController {

    private final ChatModel chatModel = new ChatModel();
    private final ChatMenuView view;

    public ChatMenuController(Model model, Controller parent) {
        view = new ChatMenuView(model, chatModel);
        view.setOnSend(() -> sendMessage());
        view.setOnMainMenu(() -> parent.showMainMenu());
        // chatModel.usersProperty().add("Bomberman");
    }

    private void sendMessage() {
        // System.out.println("Input was: [" + chatModel.inputProperty().get() + "]");
        String text = chatModel.inputProperty().get().trim();
        if (!text.isEmpty()) {
            chatModel.addMessage("You: " + text);
            chatModel.inputProperty().set("");
        }
    }

    public ChatMenuView getView() {
        return view;
    }
}
