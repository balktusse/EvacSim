import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Obstacle implements IObstacle{
    private final List<Point2D> points;
    private Point2D force_corner;

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    public Obstacle(Point2D top_right, Point2D bottom_left, Point2D force_corner){
        this.points = new ArrayList<>();
        this.points.add(top_right);
        this.points.add(bottom_left);
        if(force_corner != null && (force_corner.equals(points.get(0)) || force_corner.equals(points.get(1)))){
            this.force_corner = force_corner;
        }
    }

    @Override
    public double distanceTo(Point2D other_position){
        double x1 = Math.min(points.get(0).getX(), points.get(1).getX());
        double x2 = Math.max(points.get(0).getX(), points.get(1).getX());
        double y1 = Math.min(points.get(0).getY(), points.get(1).getY());
        double y2 = Math.max(points.get(0).getY(), points.get(1).getY());

        double clampedX = clamp(other_position.getX(), x1, x2);
        double clampedY = clamp(other_position.getY(), y1, y2);

        return other_position.distance(clampedX, clampedY);
    }

    @Override
    public List<Point2D> getPosition(){
        return this.points;
    }

    public void setForceCorner(Point2D force_corner){
        if(force_corner.equals(points.get(0)) || force_corner.equals(points.get(1))){
            this.force_corner = force_corner;
        }
    }

    public Point2D getForceCorner(){ return this.force_corner; }
}


