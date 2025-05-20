import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Exit implements IExit{
    private final int id;
    private final List<Point2D> position;
    private final int capacity;

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    public Exit(int id, Point2D left, Point2D right, int capacity)
    {
        this.id = id;
        this.position = new ArrayList<>();
        this.position.add(left);
        this.position.add(right);
        this.capacity = capacity;
    }

    @Override
    public boolean isAt(Point2D given_position) {
        double x = given_position.getX();
        double y = given_position.getY();

        double minX = Math.min(position.get(0).getX(), position.get(1).getX());
        double maxX = Math.max(position.get(0).getX(), position.get(1).getX());
        double yLine = position.get(0).getY(); // assume both have same Y

        return (x >= minX && x <= maxX) && (y == yLine);
    }

    @Override
    public double distanceTo(Point2D other_position) {
        double x1 = Math.min(position.get(0).getX(), position.get(1).getX());
        double x2 = Math.max(position.get(0).getX(), position.get(1).getX());
        double y1 = Math.min(position.get(0).getY(), position.get(1).getY());
        double y2 = Math.max(position.get(0).getY(), position.get(1).getY());

        double clampedX = clamp(other_position.getX(), x1, x2);
        double clampedY = clamp(other_position.getY(), y1, y2);

        return other_position.distance(clampedX, clampedY);
    }

    @Override
    public List<Point2D> getPosition(){
        return this.position;
    }
}
