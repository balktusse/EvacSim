import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public interface IMagnet {

    Point2D computeResultForce(Agent agent, List<Agent> agents, List<Obstacle> obstacles, List<Exit> exits);
}
