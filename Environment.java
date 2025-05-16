import javafx.geometry.Point2D;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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

    @Override
    public void addAgents(int num_agents){
        if(map != null){
            Point2D position = map.getRandomPosition();
            for(int id = 0; id < num_agents; id++){
                while (freePosition(position) != true){
                    position = map.getRandomPosition();
                }
                Agent agent = new Agent(id, position);
                agents.add(agent);

            }
        }
    }

    @Override
    public void removeAgent(Agent agent){
        agents.remove(agent);
    }

    @Override
    public void addObstacle(Point2D top_right, Point2D bottom_left){
        if(map != null){
            Obstacle obstacle = new Obstacle(top_right, bottom_left);
            double x_axis = top_right.getX() - bottom_left.getX();
            double y_axis = top_right.getY() - bottom_left.getY();

            // checking so that every point for the obstacle's area is free
            for(int x = 0; x < x_axis; x++){
                for(int y = 0; y < y_axis; y++){
                    if(freePosition(x,y) != true){
                        System.out.println("Obstacle overrides another object!");
                        return;
                    }
                }
            }
            obstacles.add(obstacle);
        }
    }

    @Override
    public void addExit(Point2D position, int capacity){
        int id = exits.size();
        Exit exit = new Exit(id, position, capacity);
        exits.add(exit);
    }

    @Override
    public void setMagnet(double force_agent, double force_wall, double force_exit, double force_object){
        this.magnet = new Magnet(force_agent, force_wall, force_exit, force_object);
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
}
