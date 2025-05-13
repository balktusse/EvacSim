import javafx.geometry.Point2D;

public class main {

    public static void main(String[] args) {
        // Create a new Agent with ID 1 at position (0, 0)
        Agent agent = new Agent(1, new Point2D(0, 0));

        // Apply a force (velocity)
        agent.applyForce(new Point2D(2, 3));
        System.out.println("Velocity applied: " + agent.velocity); // Should be (2.0, 3.0)

        // Update the agent's position
        agent.update(new Point2D(5, 5));
        System.out.println("Updated position: " + agent.position); // Should be (5.0, 5.0)

        // Change the agent's status
        agent.changeStatus(true);
        System.out.println("Status changed to: " + agent.status); // Should be true

        // Calculate distance to a point
        double distance = agent.distanceTo(new Point2D(8, 9));
        System.out.println("Distance to (8,9): " + distance); // Should be approx 5.0
    }
}
