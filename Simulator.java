public class Simulator {

    private Environment environment;
    private DataCollector data_collector;
    private boolean paused = false;
    private boolean running = false;

    public Simulator(Environment environment, DataCollector data_collector) {
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
                    Thread.sleep(1000);
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
            for (Agent agent : environment.getAgents()) {
                agent.update();
                if (/*evacuated agents*/) {
                    environment.removeAgent(agent);
                    data_collector.collectData(1);
                }
            }
            data_collector.advanceTime(1.0);

            visualization.update();

            return true;
        } catch (Exception e) {
            System.err.println("Error during simulation update: " + e.getMessage());
            return false;
        }
    }

    public boolean reset() {
        try {
            environment.clear();
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
            environment.remove();
            data_collector = new DataCollector();
            System.out.println("Simulation stopped.");
            return true;
        } catch (Exception e) {
            System.err.println("Error during stop: " + e.getMessage());
            return false;
        }
    }
}
