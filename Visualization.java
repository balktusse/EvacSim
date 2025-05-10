import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.List;

public class Visualization implements IVisualization {
    private String view_type;
    private Environment environment;
    private Pane renderPane;  // JavaFX container for rendering

    public Visualization(String view_type, Environment environment, Pane renderPane) {
        this.view_type = view_type;
        this.environment = environment;
        this.renderPane = renderPane;
    }

    @Override
    public void render() {
        renderPane.getChildren().clear(); // Clear previous render

        // Render Agents
        for (Agent agent : environment.getAgents()) {
            Circle agentCircle = new Circle(agent.getPosition().getX(), agent.getPosition().getY(), 5, Color.BLUE);
            renderPane.getChildren().add(agentCircle);
        }

        // Render Obstacles
        for (Obstacle obstacle : environment.getObstacles()) {
            Rectangle obsRect = new Rectangle(obstacle.getPosition().getX(), obstacle.getPosition().getY(), 10, 10);
            obsRect.setFill(Color.GRAY);
            renderPane.getChildren().add(obsRect);
        }

        // Render Exits
        for (Exit exit : environment.getExits()) {
            Rectangle exitRect = new Rectangle(exit.getPosition().getX(), exit.getPosition().getY(), 15, 15);
            exitRect.setFill(Color.GREEN);
            renderPane.getChildren().add(exitRect);
            renderPane.getChildren().add(new Text(exit.getPosition().getX(), exit.getPosition().getY() - 5, "Exit " + exit.getId()));
        }
    }

    @Override
    public void update() {
        // For now, update is just re-rendering the scene
        render();
    }
}