import javafx.geometry.Point2D;
import java.util.List;

public interface IObstacle {
    double distanceTo(Point2D position);
    List<Point2D> getPosition();
}