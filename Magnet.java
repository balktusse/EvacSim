import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Magnet implements IMagnet{

    private final double force_agent;
    private final double force_wall;
    private final double force_exit;
    private final double force_object;

    public Magnet(double force_agent, double force_wall, double force_exit, double force_object) {
        this.force_agent = force_agent;
        this.force_wall = force_wall;
        this.force_exit = force_exit;
        this.force_object = force_object;
    }

    @Override
    public Point2D computeResultForce(Agent agent, List<Agent> agents, List<Obstacle> obstacles, List<Exit> exits) {
        Point2D result_force = new Point2D(0, 0);
        Point2D agent_pos = new Point2D(agent.getPosition().getX(), agent.getPosition().getY());

        if (obstacles != null) {
            for (Obstacle obs : obstacles) {
                double distance = obs.distanceTo(agent_pos);
                if (distance != 0) {
                    double epsilon = 0.001;

                    double dx = obs.distanceTo(agent_pos.add(epsilon, 0)) - distance;
                    double dy = obs.distanceTo(agent_pos.add(0, epsilon)) - distance;

                    Point2D gradient = new Point2D(dx, dy).normalize();
                    Point2D repulse = gradient.multiply(force_wall / (distance * distance));

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

        for (Exit exit : exits) {
            double distance = exit.distanceTo(agent_pos);
            if (distance != 0) {
                // Estimate direction toward the exit via gradient
                double epsilon = 0.001;

                double dx = exit.distanceTo(agent_pos.add(epsilon, 0)) - distance;
                double dy = exit.distanceTo(agent_pos.add(0, epsilon)) - distance;

                Point2D gradient = new Point2D(dx, dy).normalize();
                Point2D attract = gradient.multiply(-force_exit / distance);  // negative gradient = attraction

                result_force = result_force.add(attract);
            }
        }


        return result_force;
    }
}
