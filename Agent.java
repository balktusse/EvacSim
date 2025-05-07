public class Agent implements IAgent{

    private int id;
    private boolean status;
    private Vector2D position;
    private Vector2D velocity;

    public Agent(int id, Vector2D start_position){
        this.id = id;
        this.position = start_position;
        this.velocity = new Vector2D(0, 0);
        this.status = false;
    }

    @Override
    public void update(Vector2D new_position) {
        this.position = new_position;
    }

    @Override
    public void changeStatus(boolean new_status) {
        this.status = new_status;
    }

    @Override
    public void applyForce(Vector2D force) {
        this.velocity = force;
    }

    @Override
    public double distanceTo(Vector2D position) {
        return 0;
    }
}
