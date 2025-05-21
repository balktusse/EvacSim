import javafx.geometry.Point2D;

public interface IEnvironment {
    void addAgents(int num_agents);
    void removeAgent(Agent agent);
    void addObstacle(Point2D top_right, Point2D bottom_left, Point2D force_corner);
    void addExit(Point2D left, Point2D right, int capacity);
    void setMagnet(double force_agent, double force_wall, double force_exit, double force_object);
    void setMap(int width, int height);
    boolean freePosition(Point2D position);
}