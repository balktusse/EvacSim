import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.stage.Stage;

public class SimulatorTest extends Application {

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        Simulator simulator = new Simulator();
        simulator.magnet();
        simulator.createMap();
        //simulator.createObstacle(new Point2D(0, 0), new Point2D(5, 50));
        //simulator.createObstacle(new Point2D(0, 60), new Point2D(5, 400));

        simulator.createObstacle(new Point2D(200, 300), new Point2D(180, 200));
        simulator.createObstacle(new Point2D(50, 50), new Point2D(51, 51));
        simulator.createExit(new Point2D(0, 50), new Point2D(3, 60));
        simulator.addAgents(1000);
        System.out.println("Agents in environment: " + simulator.getAgentPositions().size());

        Visualization visualization = new Visualization(simulator);
        visualization.start(primaryStage); // ✅ Use the JavaFX-provided stage here

        System.out.println("Starting simulation...");
        //simulator.run();

        // You should NOT use Thread.sleep here in JavaFX UI thread — consider using Timeline instead
    }

    public static void main(String[] args) {
        launch(args); // ✅ This calls the JavaFX lifecycle, and triggers start(Stage)
    }
}
