import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.stage.Stage;

public class SimulatorTest extends Application {

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        Simulator simulator = new Simulator();
        //simulator.addAgents(1000);
        System.out.println("Agents in environment: " + simulator.getAgentPositions().size());

        Visualization visualization = new Visualization(simulator);
        visualization.start(primaryStage); // ✅ Use the JavaFX-provided stage here

        System.out.println("Starting simulation...");

        // You should NOT use Thread.sleep here in JavaFX UI thread — consider using Timeline instead
    }

    public static void main(String[] args) {
        launch(args); // ✅ This calls the JavaFX lifecycle, and triggers start(Stage)
    }
}
