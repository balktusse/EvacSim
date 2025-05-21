import javafx.geometry.Point2D;

public interface IEnvironment {
    void addAgents(int num_agents);
    void removeAgent(Agent agent);
    void addObstacle(Point2D top_right, Point2D bottom_left);
    void addExit(Point2D left, Point2D right, int capacity);
    void setMagnet(double force_agent, double force_exit, double force_object, double force_attractor, double force_repeller);
    void setMap(int width, int height);
    boolean freePosition(Point2D position);
}