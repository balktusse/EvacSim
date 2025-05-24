import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
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
    private Button startBtn, pauseBtn, resumeBtn, stopBtn, resetBtn;
    private Thread renderThread;
    private int numAgents;


    public Visualization(Simulator simulator) {
        this.simulator = simulator;
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showStartMenu();
    }

    private void showStartMenu() {
        Text title = new Text("Simulation Menu");
        TextField agentNumField = new TextField();
        agentNumField.setPromptText("Number of Agents");
        agentNumField.setPrefColumnCount(9);

        HBox inputBox = new HBox(10, agentNumField);
        inputBox.setAlignment(Pos.CENTER);

        Button startSimulationBtn = new Button("Start Simulation");
        startSimulationBtn.setOnAction(e -> {
            try {
                numAgents = Integer.parseInt(agentNumField.getText());
                if (numAgents <= 0) {
                    agentNumField.setStyle("-fx-border-color: red;");
                    return;
                }
                showSimulationUI();
            } catch (NumberFormatException ex) {
                agentNumField.setStyle("-fx-border-color: red;");
            }
        });

        VBox menuLayout = new VBox(20, title, inputBox, startSimulationBtn);
        menuLayout.setAlignment(Pos.CENTER);
        Scene menuScene = new Scene(menuLayout, 500, 500);

        primaryStage.setTitle("Start Menu");
        primaryStage.setScene(menuScene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        Platform.runLater(() -> menuLayout.requestFocus());
    }

    private void showSimulationUI() {
        renderPane = new Pane();

        initializeButtons();
        updateButtonStates(false, true, true, false, true);

        HBox controls = new HBox(10, startBtn, pauseBtn, resumeBtn, stopBtn, resetBtn);
        controls.setAlignment(Pos.CENTER);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        BorderPane root = new BorderPane();
        root.setCenter(renderPane);
        root.setBottom(controls);

        Scene simulationScene = new Scene(root, screenWidth * 0.96, screenHeight * 0.95);

        primaryStage.setTitle("Simulation Visualization");
        primaryStage.setScene(simulationScene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        double uniformScale = Math.min((screenWidth / 800), (screenHeight * 0.90 / 400));
        scaleX = uniformScale;
        scaleY = uniformScale;

        renderStaticObjects();
        startRenderingLoop();
    }

    private void initializeButtons() {
        startBtn = new Button("Start");
        pauseBtn = new Button("Pause");
        resumeBtn = new Button("Resume");
        stopBtn = new Button("Stop");
        resetBtn = new Button("Reset");

        startBtn.setOnAction(e -> {
            simulator.run();
            updateButtonStates(true, false, true, false, false);
        });

        pauseBtn.setOnAction(e -> {
            simulator.pause();
            updateButtonStates(true, true, false, false, false);
        });

        resumeBtn.setOnAction(e -> {
            simulator.resume();
            updateButtonStates(true, false, true, false, false);
        });

        stopBtn.setOnAction(e -> {
            simulator.stop();
            showEndScreen();
        });

        resetBtn.setOnAction(e -> {
            simulator.reset();
            simulator = new Simulator();
            renderPane.getChildren().clear();
            renderStaticObjects();
            render();
            updateButtonStates(false, true, true, false, true);
        });
    }

    private void updateButtonStates(boolean start, boolean pause, boolean resume, boolean stop, boolean reset) {
        startBtn.setDisable(start);
        pauseBtn.setDisable(pause);
        resumeBtn.setDisable(resume);
        stopBtn.setDisable(stop);
        resetBtn.setDisable(reset);
    }

    private void showEndScreen() {
        Text title = new Text("Simulation Ending");
        Button newSimulationBtn = new Button("New Simulation");
        newSimulationBtn.setOnAction(e -> {
            simulator.reset();
            simulator = new Simulator();
            showStartMenu();
        });

        VBox menuLayout = new VBox(20, title, newSimulationBtn);
        menuLayout.setAlignment(Pos.CENTER);
        Scene menuScene = new Scene(menuLayout, 500, 500);

        primaryStage.setTitle("Ending Screen");
        primaryStage.setScene(menuScene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        Platform.runLater(() -> menuLayout.requestFocus());
    }

    private void startRenderingLoop() {
        renderThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(50);
                    Platform.runLater(() -> {
                        if (simulator.isRunning() && !simulator.isPaused()) {
                            simulator.update();
                        }
                        render();
                        
                        if (simulator.getEvacuatedAgents() == numAgents){
                            simulator.stop();
                            if (renderThread != null && renderThread.isAlive()) {
                                renderThread.interrupt();
                            }
                            showEndScreen();
                        }
                    });
                }
            } catch (InterruptedException e) {
                // Thread interrupted
            }
        });
        renderThread.setDaemon(true);
        renderThread.start();
    }

    private void render() {
        renderPane.getChildren().removeIf(node -> node instanceof Circle);
        for (Point2D p : simulator.getAgentPositions()) {
            Circle agentCircle = new Circle(p.getX() * scaleX, p.getY() * scaleY, 5, Color.BLUE);
            renderPane.getChildren().add(agentCircle);
        }
    }

    private void renderStaticObjects() {
        simulator.addAgents(numAgents);
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