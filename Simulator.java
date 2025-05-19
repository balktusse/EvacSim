import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private Environment environment;
    private DataCollector data_collector;
    private boolean paused = false;
    private boolean running = false;

    public Simulator() {
        this.environment = new Environment();
        this.data_collector = new DataCollector();
    }

    public boolean run() {
        if(running){return false;}

        paused = false;
        running = true;

        new Thread(() -> {
            while (running) {
                if (!paused) {
                    update();
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Simulation interrupted.");
                    break;
                }
            }
        }).start();

        return true;
    }

    public boolean update() {
        // simulation paused, return that the state is updated with no change
        if (paused){
            System.out.println("Simulation paused. No updates performed.");
            return true;
        }

        try {
            environment.update();
            List<Agent> safe_guys = environment.evacuated();

            for (Agent agent : safe_guys) {
                environment.removeAgent(agent);
                data_collector.collectData(1);
            }
            
            data_collector.advanceTime(1.0);

            //visualization.update();

            return true;
        } catch (Exception e) {
            System.err.println("Error during simulation update: " + e.getMessage());
            return false;
        }
    }

    public boolean reset() {
        try {
            environment = new Environment();
            data_collector = new DataCollector();
            paused = false;
            System.out.println("Simulation reset.");
            return true;
        } catch (Exception e) {
            System.err.println("Error during reset: " + e.getMessage());
            return false;
        }
    }

    public boolean pause() {
        paused = true;
        System.out.println("Pause enabled.");
        return true;
    }

    public boolean resume(){
        paused = false;
        System.out.println("Resume enabled.");
        return true;
    }

    public boolean stop() {
        try {
            paused = true;
            //environment.remove();
            data_collector = new DataCollector();
            System.out.println("Simulation stopped.");
            return true;
        } catch (Exception e) {
            System.err.println("Error during stop: " + e.getMessage());
            return false;
        }
    }

    public void createMap(){
        environment.setMap(400, 400);
    }

    public void createObstacle(){
        Point2D top_right = new Point2D(20, 20);
        Point2D bottom_left = new Point2D(40, 60);
        environment.addObstacle(top_right, bottom_left);
    }

    public void addAgents(int number){
        environment.addAgents(number);
    }

    public void magnet(){
        environment.setMagnet(2, 2, 2,5);
    }

    public boolean isRunning(){
        return running;
    }

    public boolean isPaused(){
        return paused;
    }

    public List<Point2D> getAgentPositions(){
        return environment.getAgentPositions();
    }

    public List<Point2D> getObstaclePositions(){
        return environment.getObstaclePosition();
    }

    public List<Exit> getExitPositions(){
        return environment.getExits();  
    }

}
