import javafx.geometry.Point2D;

public class Magnet implements IMagnet{

    private double force_agent;
    private double force_wall;
    private double force_exit;
    private double force_object;

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
                Point2D obs_pos = new Point2D(obs.getPosition().getX(), obs.getPosition().getY());
                Point2D diff = agent_pos.subtract(obs_pos);
                double distance_sq = diff.magnitudeSquared();
                if (distance_sq != 0) {
                    Point2D repulse = diff.normalize().multiply(force_wall / distance_sq);
                    result_force = result_force.add(repulse);
                }
            }
        }

        if (agents != null) {
            for (Agent other : agents) {
                if (other != agent) {
                    Point2D other_pos = other.getPosition();
                    Point2D diff = agent_pos.subtract(other_pos);
                    double distance_sq = diff.magnitudeSquared();
                    if (distance_sq != 0) {
                        Point2D repulse = diff.normalize().multiply(force_agent / distance_sq);
                        result_force = result_force.add(repulse);
                    }
                }
            }
        }

        for (Exit exit : exits) {
            Point2D exit_pos = new Point2D(exit.getPosition().getX(), exit.getPosition().getY());
            Point2D diff = exit_pos.subtract(agent_pos);
            double distance = diff.magnitude();
            if (distance != 0) {
                Point2D attract = diff.normalize().multiply(force_exit / distance);
                result_force = result_force.add(attract);
            }
        }


        return result_force;
    }
}
