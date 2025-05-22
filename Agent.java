import javafx.geometry.Point2D;

public class Agent implements IAgent{

    private final int id;
    private boolean status;
    private Point2D position;
    private Point2D velocity;
    private static final double MAX_SPEED = 3.0;


    public Agent(int id, Point2D start_position){
        this.id = id;
        this.position = start_position;
        this.velocity = new Point2D(0, 0);
        this.status = false;
    }

    @Override
    public Point2D getPosition(){
        return this.position;
    }

    public boolean getStatus() { return this.status; }

    @Override
    public void step() {
        this.position = this.position.add(this.velocity);

    }

    @Override
    public void changeStatus(boolean new_status) {
        this.status = new_status;
    }

    @Override
    public void applyForce(Point2D force) {
        double damping = 0.95;
        this.velocity = this.velocity.multiply(damping).add(force);
        if (velocity.magnitude() > MAX_SPEED) {
            velocity = velocity.normalize().multiply(MAX_SPEED);
        }

    }

    @Override
    public double distanceTo(Point2D position) {
        return this.position.distance(position);
    }

}
