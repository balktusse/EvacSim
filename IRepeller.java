import javafx.geometry.Point2D;

public interface IRepeller {
    double distanceTo(Point2D position);
    Point2D getPosition();
}
