import javafx.geometry.Point2D;

public class Obstacle implements IObstacle{
    private final int id;
    private final Point2D position;

    public Obstacle(int id, Point2D position){
        this.id = id;
        this.position = position;
    }

    @Override
    public double distanceTo(Point2D other_position){
        return this.position.distance(other_position);
    }

    @Override
    public Point2D getPosition(){
        return this.position;
    }
}
