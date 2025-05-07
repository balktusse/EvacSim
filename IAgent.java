public interface IAgent {

    void update(Vector2D new_position);

    void changeStatus(boolean new_status);

    void applyForce(Vector2D force);

    double distanceTo(Vector2D position);
}
