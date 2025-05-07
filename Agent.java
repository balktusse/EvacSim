public class Agent implements IAgent{

    private int id;
    private boolean status;
    private Vector2D position;
    private Vector2D velocity;

    public Agent(int id, Vector2D startPosition){
        this.id = id;
        this.position = startPosition;
        this.velocity = new Vector2D(0, 0);
        this.status = false;
    }

    @Override
    public void update(Vector2D newPosition) {
        this.position = newPosition;
    }

    @Override
    public void changeStatus(boolean newStatus) {
        this.status = newStatus;
    }

    @Override
    public void applyForce(Vector2D force) {

    }

    @Override
    public double distanceTo(Vector2D position) {
        return 0;
    }


}
