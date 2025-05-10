import javafx.geometry.Point2D;

public class Exit implements IExit{
    private int id;
    private Point2D position;
    private int capacity;

    public Exit(int Id, Point2D position, int capacity)
    {
        this.id = id;
        this.position = position;
        this.capacity = capacity;
    }

    @Override
    public bool isAt(Point2D given_position)
    {
        // checks if given position is the position of the exit
        return(given_position == this.position)? True : False;
    }

    @Override
    public double distanceTo(Point2D given_position)
    {
        int temp = 27;
        return temp;

    }
}
