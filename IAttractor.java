import javafx.geometry.Point2D;

public interface IAttractor {
    double distanceTo(Point2D position);
    Point2D getPosition();
}