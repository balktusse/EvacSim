import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private Environment environment;
    private DataCollector data_collector;
    private boolean paused = false;
    private boolean running = false;
    private long lastUpdateTime;
    private List<Agent> evacuated_agents;

    public Simulator() {
        this.environment = new Environment();
        this.data_collector = new DataCollector();
        this.evacuated_agents = new ArrayList<>();
    }

    public boolean run() {
        if(running){return false;}
        lastUpdateTime = System.nanoTime();
        paused = false;
        running = true;

        new Thread(() -> {
            while (running) {
                if (!paused) {
                    update();
                }

                try {
                    Thread.sleep(50);
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
            long now = System.nanoTime();
            double deltaTime = (now - lastUpdateTime) / 1_000_000_000.0;  // seconds
            lastUpdateTime = now;
            environment.update();
            List<Agent> evacuatedAgents = environment.evacuated();
            evacuated_agents.addAll(evacuatedAgents);
            for (Agent agent : evacuatedAgents) {
                environment.removeAgent(agent);
            }
            data_collector.collect_data(evacuatedAgents.size());
            data_collector.advanceTime(deltaTime);

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
            //data_collector = new DataCollector();
            System.out.println("Simulation stopped.");
            return true;
        } catch (Exception e) {
            System.err.println("Error during stop: " + e.getMessage());
            return false;
        }
    }

    public void addAgents(int number){
        environment.addAgents(number);
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
        return environment.getObstaclePositions();
    }

    public List<Point2D> getExitPositions(){
        return environment.getExitPositions();
    }

    public int getEvacuatedAgents(){
        return evacuated_agents.size();
    }

    public int getEvacuatedCount(){
        return data_collector.getTotalEvacuated();
    }

    public double getElapsedTime() {
        return data_collector.getElapsedTime();  // Make sure DataCollector supports this
    }
}
