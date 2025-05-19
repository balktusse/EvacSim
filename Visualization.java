import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.Point2D;

public class Visualization {
    private Simulator simulator;
    private Pane renderPane;
    private double scaleX = 1.0;
    private double scaleY = 1.0;

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
        Button resetBtn = new Button("Reset");

        startBtn.setOnAction(e -> simulator.run());
        pauseBtn.setOnAction(e -> simulator.pause());
        resumeBtn.setOnAction(e -> simulator.resume());
        stopBtn.setOnAction(e -> simulator.stop());
        resetBtn.setOnAction(e -> simulator.reset());

        HBox controls = new HBox(10, startBtn, pauseBtn, resumeBtn, stopBtn, resetBtn);

        BorderPane root = new BorderPane();
        root.setCenter(renderPane);
        root.setBottom(controls);

        Scene scene = new Scene(root, 500, 500);

        primaryStage.setTitle("Simulation Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Adjust scaling based on map size (assuming map is 100x100; adjust as needed)
        scaleX = 400.0 / 100.0; // Map width to pane width
        scaleY = 400.0 / 100.0; // Map height to pane height

        startRenderingLoop();
    }

    private void startRenderingLoop() {
        Thread renderThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(100);
                    if (simulator.isRunning() && !simulator.isPaused()) {
                        Platform.runLater(() -> {
                            simulator.update();
                            render();
                        });
                    } else {
                        Platform.runLater(this::render);
                    }
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

        // Render agents
        for (Point2D p : simulator.getAgentPositions()) {
            System.out.println(p);
            Circle agentCircle = new Circle(p.getX() * scaleX, p.getY() * scaleY, 5, Color.BLUE);
            renderPane.getChildren().add(agentCircle);
        }

        // Render obstacles
        /*
        for (Point2D p : simulator.getObstaclePositions()) {
            Rectangle obsRect = new Rectangle(p.getX() * scaleX, p.getY() * scaleY, 10, 10);
            obsRect.setFill(Color.GRAY);
            renderPane.getChildren().add(obsRect);
        }

        // Render exits
        for (Point2D p : simulator.getExitPositions()) {
            Rectangle exitRect = new Rectangle(p.getX() * scaleX, p.getY() * scaleY, 15, 15);
            exitRect.setFill(Color.GREEN);
            renderPane.getChildren().add(exitRect);
        }
        */
    }
}
