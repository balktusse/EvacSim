
import javafx.geometry.Point2D;
import java.util.Random;

public class Map implements IMap{
    private int width;
    private int height;

    public Map(int width, int height){
        this.width = width;
        this.height = height;
    }

    // assuming the map is located in corner
    // (0,0), (0,y), (x,0), (x,y)
    @Override
    public boolean isInside(Point2D position){
        double x = position.getX();
        double y = position.getY();

        return(x<=width && y<= height && width > 0 && height > 0)? true : false;
    }

    @Override
    public Point2D getRandomPosition(){
        Point2D randPoint = getRandomPoint(0,width,0,height);
        return randPoint;
    }

    private Point2D getRandomPoint(double minX, double maxX, double minY, double maxY) {
        Random random = new Random();
        double x = minX + (maxX - minX) * random.nextDouble();
        double y = minY + (maxY - minY) * random.nextDouble();
        return new Point2D(x, y);
    }
}
