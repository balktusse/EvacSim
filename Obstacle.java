public class Obstacle implements IObstacle{
    private int id;
    private Vector2D position;

    public Obstacle(int id, Vector2D position){
        this.id = id;
        this.position = position;
    }

    @Override
    public double distanceTo(Vector2D position){
        return 0;
    }
}
