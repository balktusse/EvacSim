import javafx.geometry.Point2D;

public interface IExit {

    boolean isAt(Point2D given_position);

    double distanceTo(Point2D given_position);

}
