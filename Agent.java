import javafx.geometry.Point2D;

public class Agent implements IAgent{

    private int id;
    private boolean status;
    private Point2D position;
    private Point2D velocity;

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

    @Override
    public void update(Point2D new_position) {
        this.position = new_position;
    }

    @Override
    public void changeStatus(boolean new_status) {
        this.status = new_status;
    }

    @Override
    public void applyForce(Point2D force) {
        this.velocity = force;
    }

    @Override
    public double distanceTo(Point2D position) {
        return this.position.distance(position);
    }
}
