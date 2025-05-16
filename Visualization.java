import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.Point;

public class Visualization {
    private Simulator simulator;
    private Pane renderPane;

    public Visualization(Simulator simulator) {
        this.simulator = simulator;
    }

    public void start(Stage primaryStage) {
        renderPane = new Pane();
        renderPane.setPrefSize(400, 400);

        Button startBtn = new Button("Start");
        Button pauseBtn = new Button("Pause");
        Button resumeBtn = new Button("Resume");
        Button stopBtn = new Button("Stop");

        startBtn.setOnAction(e -> simulator.run());
        pauseBtn.setOnAction(e -> simulator.pause());
        resumeBtn.setOnAction(e -> simulator.resume());
        stopBtn.setOnAction(e -> simulator.stop());

        HBox controls = new HBox(10, startBtn, pauseBtn, resumeBtn, stopBtn);

        BorderPane root = new BorderPane();
        root.setCenter(renderPane);
        root.setBottom(controls);

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("Simulation Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();

        startRenderingLoop();
    }

    private void startRenderingLoop() {
        Thread renderThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(100);
                    Platform.runLater(this::render);
                }
            } catch (InterruptedException e) {
                // Exit thread
            }
        });
        renderThread.setDaemon(true);
        renderThread.start();
    }

    private void render() {
        renderPane.getChildren().clear();

        for (Point p : simulator.getAgentPositions()) {
            Circle agentCircle = new Circle(p.getX(), p.getY(), 5, Color.BLUE);
            renderPane.getChildren().add(agentCircle);
        }

        for (Point p : simulator.getObstaclePositions()) {
            Rectangle obsRect = new Rectangle(p.getX(), p.getY(), 10, 10);
            obsRect.setFill(Color.GRAY);
            renderPane.getChildren().add(obsRect);
        }

        for (Point p : simulator.getExitPositions()) {
            Rectangle exitRect = new Rectangle(p.getX(), p.getY(), 15, 15);
            exitRect.setFill(Color.GREEN);
            renderPane.getChildren().add(exitRect);
        }
    }
}