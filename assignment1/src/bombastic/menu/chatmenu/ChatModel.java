package bombastic.menu.chatmenu;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class ChatModel {

    private final ListProperty<String> users =
            new SimpleListProperty<>(FXCollections.observableArrayList(List.of("Bent", "Karen", "Sammy")));
    private final StringProperty messages = new SimpleStringProperty("");

    public ListProperty<String> usersProperty() {
        return users;
    }

    public StringProperty messagesProperty() {
        return messages;
    }

    public void addMessage(String message) {
        messages.set(messages.get() + message + "\n");
        // System.out.println("All messages so far:\n" + messages.get());
    }
}
