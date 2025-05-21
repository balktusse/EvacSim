import javafx.geometry.Point2D;

public class Attractor implements IAttractor {
    private final int id;
    private final Point2D position;
    private final int capacity;

    public Attractor(int id, Point2D position, int capacity) {
        this.id = id;
        this.position = position;
        this.capacity = capacity;
    }

    @Override
    public double distanceTo(Point2D other_position) {
        return position.distance(other_position);
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }
}