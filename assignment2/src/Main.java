import bombastic.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        controller = new Controller();
        Scene scene = new Scene(controller.getView(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("/css/default.css").toExternalForm());
        stage.setTitle("Bombastic");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        controller.shutdown();
    }
}