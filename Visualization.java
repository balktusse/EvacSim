import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

public class Visualization {
    private Simulator simulator;
    private Pane renderPane;
    private double scaleX = 1.0;
    private double scaleY = 1.0;
    private Stage primaryStage;

    public Visualization(Simulator simulator) {
        this.simulator = simulator;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showStartMenu();
    }

    private void showStartMenu() {
        Text title = new Text("Simulation Menu");

        TextField agentnum = new TextField();
        agentnum.setPromptText("Number of Agents");
        agentnum.setPrefColumnCount(9);

        HBox inputBox = new HBox(10, agentnum);
        inputBox.setAlignment(Pos.CENTER);

        Button startSimulationBtn = new Button("Start Simulation");
        startSimulationBtn.setOnAction(e -> {
            try {
                int number = Integer.parseInt(agentnum.getText());
                showSimulationUI(number);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number entered.");
                // You can also show a popup or highlight the field in red here
            }
        });

        VBox menuLayout = new VBox(20, title, inputBox, startSimulationBtn);
        menuLayout.setAlignment(Pos.CENTER);
        Scene menuScene = new Scene(menuLayout, 500, 500);

        primaryStage.setTitle("Start Menu");
        primaryStage.setScene(menuScene);
        primaryStage.show();

        Platform.runLater(() -> menuLayout.requestFocus());
    }

    private void showSimulationUI(int number) {
        renderPane = new Pane();
        renderPane.setPrefSize(400, 400);

        simulator.addAgents(number);

        Button startBtn = new Button("Start");
        Button pauseBtn = new Button("Pause");
        Button resumeBtn = new Button("Resume");
        Button stopBtn = new Button("Stop");
        Button resetBtn = new Button("Reset");

        startBtn.setOnAction(e -> simulator.run());
        pauseBtn.setOnAction(e -> simulator.pause());
        resumeBtn.setOnAction(e -> simulator.resume());
        stopBtn.setOnAction(e -> {
            try {
                simulator.stop();
                showEndScreen();
            } catch (NumberFormatException ex) {
                System.out.println("Error");
            }
        });
        resetBtn.setOnAction(e -> {
            try {
                simulator.reset();
                simulator.pause();
                showSimulationUI(number);
            } catch (NumberFormatException ex) {
                System.out.println("Error");
            }
        });

        HBox controls = new HBox(10, startBtn, pauseBtn, resumeBtn, stopBtn, resetBtn);
        controls.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(renderPane);
        root.setBottom(controls);

        Scene simulationScene = new Scene(root, 1650, 850);

        primaryStage.setTitle("Simulation Visualization");
        primaryStage.setScene(simulationScene);
        primaryStage.centerOnScreen();

        // Adjust scaling based on map size (adjust 200.0 to match your map size)
        scaleX = 400.0 / 200.0;
        scaleY = 400.0 / 200.0;

        renderStaticObjects();
        startRenderingLoop();
    }

    private void showEndScreen() {
        Text title = new Text("Simulation Ending");

        VBox menuLayout = new VBox(20, title);
        menuLayout.setAlignment(Pos.CENTER);
        Scene menuScene = new Scene(menuLayout, 500, 500);

        primaryStage.setTitle("Endin' Screen");
        primaryStage.setScene(menuScene);
        primaryStage.show();

        Platform.runLater(() -> menuLayout.requestFocus());
    }

    private void startRenderingLoop() {
        Thread renderThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(50);
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
                // Thread interrupted
            }
        });
        renderThread.setDaemon(true);
        renderThread.start();
    }

    private void render() {
        // Clear only agent nodes
        renderPane.getChildren().removeIf(node -> node instanceof Circle);

        for (Point2D p : simulator.getAgentPositions()) {
            Circle agentCircle = new Circle(p.getX() * scaleX, p.getY() * scaleY, 5, Color.BLUE);
            renderPane.getChildren().add(agentCircle);
        }
    }

    private void renderStaticObjects() {
        for (Point2D p : simulator.getObstaclePositions()) {
            Rectangle obsRect = new Rectangle(p.getX() * scaleX, p.getY() * scaleY, 2, 2);
            obsRect.setFill(Color.GRAY);
            renderPane.getChildren().add(obsRect);
        }

        for (Point2D p : simulator.getExitPositions()) {
            Rectangle exitRect = new Rectangle(p.getX() * scaleX, p.getY() * scaleY, 2, 2);
            exitRect.setFill(Color.GREEN);
            renderPane.getChildren().add(exitRect);
        }
    }
}