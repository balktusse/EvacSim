import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Obstacle implements IObstacle{
    private final int id;
    private final List<Point2D> points;

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    public Obstacle(int id, Point2D top_right, Point2D bottom_left){
        this.id = id;
        this.points = new ArrayList<>();
        this.points.add(top_right);
        this.points.add(bottom_left);
    }

    @Override
    public double distanceTo(Point2D other_position){
        double clampedX = clamp(other_position.getX(), points.get(1).getX(), points.get(0).getX());
        double clampedY = clamp(other_position.getY(), points.get(1).getY(), points.get(0).getY());

        return other_position.distance(clampedX, clampedY);
    }

    @Override
    public List<Point2D> getPosition(){
        return this.points;
    }
}
