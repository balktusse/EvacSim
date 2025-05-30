import javafx.geometry.Point2D;

import java.util.List;

public interface IExit {

    double distanceTo(Point2D given_position);

    List<Point2D> getPosition();

}
