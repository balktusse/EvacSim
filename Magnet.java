import javafx.geometry.Point2D;
import java.util.List;

public class Magnet implements IMagnet{
    private final double force_agent;
    private final double force_exit;
    private final double force_object;
    private final double force_attractor;
    private static final double EPSILON = 0.001;


    public Magnet(double force_agent, double force_exit, double force_object, double force_attractor) {
        this.force_agent = force_agent;
        this.force_exit = force_exit;
        this.force_object = force_object;
        this.force_attractor = force_attractor;
    }

    @Override
    public Point2D computeResultForce(Agent agent, List<Agent> agents, List<Obstacle> obstacles, List<Exit> exits, List<Attractor> attractors) {
        Point2D result_force = new Point2D(0, 0);
        Point2D agent_pos = agent.getPosition();

        if (obstacles != null) {
            for (Obstacle obs : obstacles) {
                double distance = obs.distanceTo(agent_pos);
                if (distance != 0) {

                    double dx = obs.distanceTo(agent_pos.add(EPSILON, 0)) - distance;
                    double dy = obs.distanceTo(agent_pos.add(0, EPSILON)) - distance;

                    Point2D gradient = new Point2D(dx, dy).normalize();
                    Point2D repulse = gradient.multiply(force_object / (distance * distance));

                    result_force = result_force.add(repulse);
                }
            }
        }

        if (agents != null) {
            for (Agent other : agents) {
                if (other != agent) {
                    Point2D other_pos = other.getPosition();
                    Point2D diff = agent_pos.subtract(other_pos);
                    double distance_sq = diff.getX() * diff.getX() + diff.getY() * diff.getY();
                    if (distance_sq != 0) {
                        Point2D repulse = diff.normalize().multiply(force_agent / distance_sq);
                        result_force = result_force.add(repulse);
                    }
                }
            }
        }

        // Find the closest exit
        Exit closestExit = null;
        double minDistance = Double.MAX_VALUE;

        for (Exit exit : exits) {
            double distance = exit.distanceTo(agent_pos);
            if (distance < minDistance) {
                minDistance = distance;
                closestExit = exit;
            }
        }

        // Apply force from only the closest exit
        if (closestExit != null && minDistance > 0) {

            double dx = closestExit.distanceTo(agent_pos.add(EPSILON, 0)) - minDistance;
            double dy = closestExit.distanceTo(agent_pos.add(0, EPSILON)) - minDistance;

            Point2D gradient = new Point2D(dx, dy).normalize();
            Point2D attract = gradient.multiply(-force_exit/8);

            result_force = result_force.add(attract);
        }

        // Apply force from all attractors
        double agent_pos_x = agent_pos.getX();
        double agent_pos_y = agent_pos.getY();

        if (attractors != null) {
            if(((agent_pos_x > 240 && agent_pos_x < 485) && agent_pos_y < 180) ||
                    (agent_pos_x > 440 && agent_pos_y > 280) ||
                    (agent_pos_x > 570 && agent_pos_y > 180) ||
                    ((agent_pos_x > 4 && agent_pos_x < 100) && (agent_pos_y > 230 && agent_pos_y < 241))) {
                for (Attractor attractor : attractors) {
                    double distance = attractor.distanceTo(agent_pos);
                    if (distance > 0 && distance < 300) {

                        double dx = attractor.distanceTo(agent_pos.add(EPSILON, 0)) - distance;
                        double dy = attractor.distanceTo(agent_pos.add(0, EPSILON)) - distance;

                        Point2D gradient = new Point2D(dx, dy).normalize();
                        Point2D attract = gradient.multiply(-force_attractor / 12);

                        result_force = result_force.add(attract);
                    }
                }
            }
        }

        return result_force;
    }
}