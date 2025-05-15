import javafx.geometry.Point2D;

public interface IObstacle {
    double distanceTo(Point2D position);
    Point2D getPosition();
}