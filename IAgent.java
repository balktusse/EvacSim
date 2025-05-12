import javafx.geometry.Point2D;

public interface IAgent {

    Point2D getPosition();

    void update(Point2D new_position);

    void changeStatus(boolean new_status);

    void applyForce(Point2D force);

    double distanceTo(Point2D position);
}
