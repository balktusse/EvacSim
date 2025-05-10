
import javafx.geometry.Point2D;

public interface IMap {

    public boolean isInside(Point2D position);

    public Point2D getRandomPosition();

}
