package bombastic;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;


public class View extends StackPane {
    
    public void show(Node screen) {
        getChildren().setAll(screen);
    }
}
