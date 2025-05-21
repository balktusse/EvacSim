import javafx.geometry.Point2D;
import java.util.List;

public class Magnet implements IMagnet{
    private final double force_agent;
    private final double force_exit;
    private final double force_object;
    private final double force_attractor;
    private final double force_repeller;


    public Magnet(double force_agent, double force_exit, double force_object, double force_attractor, double force_repeller) {
        this.force_agent = force_agent;
        this.force_exit = force_exit;
        this.force_object = force_object;
        this.force_attractor = force_attractor;
        this.force_repeller = force_repeller;
    }

    @Override
    public Point2D computeResultForce(Agent agent, List<Agent> agents, List<Obstacle> obstacles, List<Exit> exits, List<Attractor> attractors, List<Repeller> repellers) {
        Point2D result_force = new Point2D(0, 0);
        Point2D agent_pos = agent.getPosition();

        if (obstacles != null) {
            for (Obstacle obs : obstacles) {
                double distance = obs.distanceTo(agent_pos);
                if (distance != 0) {
                    double epsilon = 0.001;

                    double dx = obs.distanceTo(agent_pos.add(epsilon, 0)) - distance;
                    double dy = obs.distanceTo(agent_pos.add(0, epsilon)) - distance;

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
            double epsilon = 0.001;

            double dx = closestExit.distanceTo(agent_pos.add(epsilon, 0)) - minDistance;
            double dy = closestExit.distanceTo(agent_pos.add(0, epsilon)) - minDistance;

            Point2D gradient = new Point2D(dx, dy).normalize();
            Point2D attract = gradient.multiply(-force_exit/8);

            result_force = result_force.add(attract);
        }

        // Apply force from all attractors
        if (attractors != null) {
            for (Attractor attractor : attractors) {
                double distance = attractor.distanceTo(agent_pos);
                if (distance > 0 && distance < 300) {
                    double epsilon = 0.001;

                    double dx = attractor.distanceTo(agent_pos.add(epsilon, 0)) - distance;
                    double dy = attractor.distanceTo(agent_pos.add(0, epsilon)) - distance;

                    Point2D gradient = new Point2D(dx, dy).normalize();
                    Point2D attract = gradient.multiply(-force_attractor/12);

                    result_force = result_force.add(attract);
                }
            }
        }

        if (repellers != null) {
            for (Repeller repeller : repellers) {
                double distance = repeller.distanceTo(agent_pos);
                if (distance > 0 && distance < 110) {
                    double epsilon = 0.001;

                    double dx = repeller.distanceTo(agent_pos.add(epsilon, 0)) - distance;
                    double dy = repeller.distanceTo(agent_pos.add(0, epsilon)) - distance;

                    Point2D gradient = new Point2D(dx, dy).normalize();
                    Point2D repel = gradient.multiply(force_repeller/10);

                    result_force = result_force.add(repel);
                }
            }
        }

        return result_force;
    }
}