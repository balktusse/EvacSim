import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        Simulator simulator = new Simulator();
        Visualization visualization = new Visualization(simulator);
        visualization.start(primaryStage);
        System.out.println("Starting simulation...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
