public interface IMagnet {

    Vector2D computeResultForce(Agent agent, List<Obstacle> obstacles, List<Exit> exits);
}
