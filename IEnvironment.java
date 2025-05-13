import javafx.geometry.Point2D;

public interface IEnvironment {
    void addAgents(int num_agents);
    void removeAgent(Agent agent);
    void addObstacle(Point2D position);
    void addExit(Point2D position, int capacity);
    void setMagnet(double force_agent, double force_wall, double force_exit, double force_object);
    void setMap(int width, int height);
    boolean freePosition(Point2D position);
}