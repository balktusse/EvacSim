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
    public void addObstacle(Point2D position){
        // fancy code here
    }

    @Override
    public void addExit(Point2D position, int capacity){
        int id = exits.size();
        Exit exit = new Exit(id, position, capacity);
        exits.add(exit);
    }

    @Override
    public void setMagnet(double force_agent, double force_wall, double force_exit, double force_object){

    }

    @Override
    public void setMap(int width, int height){

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
