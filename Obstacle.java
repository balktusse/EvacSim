import java.awt.geom.Point2D;

public class Obstacle implements IObstacle{
    private int id;
    private Point2D.Double position;

    public Obstacle(int id, Point2D.Double position){
        this.id = id;
        this.position = position;
    }

    @Override
    public double distanceTo(Point2D position){
        return this.position.distance(position);
    }
}