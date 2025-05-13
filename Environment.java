import javafx.geometry.Point2D;
import jdk.internal.agent.Agent;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;

public class Environment implements IEnvironment{
    private List<Agent> agents;
    private List<Obstacle> obstacles;
    private List<Exit> exits;
    private Map map;
    private Magnet magnet;

    public Environment(){
        List<Agent> agents = new ArrayList<>();
        List<Obstacle> obstacles = new ArrayList<>();
        List<Exit> exits = new ArrayList<>();
    }

    @Override
    public void addAgents(int num_agents, double minX, double maxX, double minY, double maxY){
        if(map != null){
            Point2D position = getRandomPoint(minX, maxX, minY, maxY);
            for(int id = 0; id < num_agents, id++){
                while (freePosition(position) != true){
                    position = getRandomPoint(minX, maxX, minY, maxY);
                }
                agent = new Agent(id, position);
                agents.add(agent)

            }
        }
    }

    @Override
    public void removeAgent(Agent agent){

    }

    @Override
    public void addObstacle(Point2D position){

    }

    @Override
    public void addExit(Point2D position, int capacity){

    }

    @Override
    public void setMagnet(double force_agent, double force_wall, double force_exit, double force_object){

    }

    @Override
    public void setMap(int width, int height){

    }

    @Override
    boolean freePosition(Point2D position){
        for(Agent agent : agents){
            if(agent.getPosition().equals(position)){ return false; }
        }
        for(Obstacle obstacle : obstacles){
            if(obstacle.getPosition().equals(position)){ return false; }
        }
        return true;
    }
}
