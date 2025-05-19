import javafx.geometry.Point2D;

import java.util.List;
import java.util.ArrayList;

public class Environment implements IEnvironment{
    private List<Agent> agents;
    private List<Obstacle> obstacles;
    private List<Exit> exits;
    private Map map;
    private Magnet magnet;

    public Environment(){
        this.agents = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.exits = new ArrayList<>();
    }

    public void update() {
        if (map != null) {
            for (Agent agent : agents) {
                Point2D force = magnet.computeResultForce(agent, agents, obstacles, exits);
                agent.applyForce(force);
                agent.step();
            }
        }
    }

    @Override
    public void addAgents(int num_agents){

        if (this.map == null) {
            System.out.println("Map must be set before adding agents.");
            return;
        }
        
        for(int id = 0; id < num_agents; id++){
            Point2D position = map.getRandomPosition();
            while (freePosition(position) != true){
                position = map.getRandomPosition();
            }
            Agent agent = new Agent(id, position);
            agents.add(agent);
            }
        }

    @Override
    public void removeAgent(Agent agent){
        agents.remove(agent);
    }

    @Override
    public void addObstacle(Point2D top_right, Point2D bottom_left){
        if (this.map == null) {
            System.out.println("Map must be set before adding obstacles.");
            return;
        }

        double leftX = Math.min(bottom_left.getX(), top_right.getX());
        double rightX = Math.max(bottom_left.getX(), top_right.getX());
        double bottomY = Math.min(bottom_left.getY(), top_right.getY());
        double topY = Math.max(bottom_left.getY(), top_right.getY());

        int startX = (int) Math.floor(leftX);
        int endX   = (int) Math.ceil(rightX);
        int startY = (int) Math.floor(bottomY);
        int endY   = (int) Math.ceil(topY);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                Point2D temp = new Point2D(x, y);
                if (!freePosition(temp)) {
                    System.out.println("Obstacle overrides another object!");
                    return;
                }
            }
        }

        Obstacle obstacle = new Obstacle(top_right, bottom_left);
        obstacles.add(obstacle);
    }

    @Override
    public void addExit(Point2D left, Point2D right, int capacity){
        int id = exits.size();
        Exit exit = new Exit(id, left, right, capacity);
        exits.add(exit);
    }

    @Override
    public void setMagnet(double force_agent, double force_wall, double force_exit, double force_object){
        this.magnet = new Magnet(force_agent, force_wall, force_exit, force_object, 400, 400);
    }

    @Override
    public void setMap(int width, int height){
        this.map = new Map(width, height);
    }

    @Override
    public boolean freePosition(Point2D position){
        for(Agent agent : agents){
            if(agent.getPosition().equals(position)){ return false; }
        }
        for(Obstacle obstacle : obstacles){
            if(obstacle.getPosition().equals(position)){ return false; }
        }
        return true;
    }

    public List<Agent> evacuated(){
        List<Agent> evacuated = new ArrayList<>(); 
        for (Agent agent : agents){
            if(agent.getStatus() == true){
                evacuated.add(agent);
            }
        }
        return evacuated;
    }

    public List<Point2D> getAgentPositions(){
        List<Point2D> positions = new ArrayList<>();
        for(Agent agent : agents){
            positions.add(agent.getPosition());
        }
        return positions;
    }

    public List<Point2D> getObstaclePosition(){
        List<Point2D> positions = new ArrayList<>();
        for (Obstacle obstacle : obstacles) {
            List<Point2D> points = obstacle.getPosition();  // should return 2 points

            Point2D p1 = points.get(0);
            Point2D p2 = points.get(1);

            int x1 = (int) Math.floor(Math.min(p1.getX(), p2.getX()));
            int x2 = (int) Math.ceil(Math.max(p1.getX(), p2.getX()));
            int y1 = (int) Math.floor(Math.min(p1.getY(), p2.getY()));
            int y2 = (int) Math.ceil(Math.max(p1.getY(), p2.getY()));

            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    positions.add(new Point2D(x, y));
                }
            }
        }
        return positions;
    }

    public List<Agent> getAgents() {
        return this.agents;
    }


    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public List<Exit> getExits() {
        return this.exits;
    }

    public Map getMap() {
        return this.map;
    }

    public Magnet getMagnet() {
        return this.magnet;
    }
}
