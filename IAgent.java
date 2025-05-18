import javafx.geometry.Point2D;

public interface IAgent {

    Point2D getPosition();

    public void step();

    void changeStatus(boolean new_status);

    void applyForce(Point2D force);

    double distanceTo(Point2D position);
}
