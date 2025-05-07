public interface IAgent {

    void update(Vector2D newPosition);

    void changeStatus(boolean newStatus);

    void applyForce(Vector2D force);

    double distanceTo(Vector2D position);
}
