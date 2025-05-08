public class Exit implements IExit{
    private int id;
    private Vector2D position;
    private int capacity;

    public Exit(int Id, Vector2D position, int capacity)
    {
        this.id = id;
        this.position = position;
        this.capacity = capacity;
    }

    @Override
    public bool isAt(Vector2D given_position)
    {
        // checks if given position is the position of the exit
        return(given_position == this.position)? True : False;
    }

    @Override
    public double distanceTo(Vector2D given_position)
    {

    }
}
