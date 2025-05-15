public class Simulator {

    private Environment environment;
    private DataCollector data_collector;
    private boolean paused = false;

    public Simulator(Environment environment, DataCollector data_collector) {
        this.environment = environment;
        this.data_collector = data_collector;
    }

    public boolean run() {

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
                agent.move();
                if (environment.hasExited(agent)) {
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

        return true;
    }

    public boolean pause() {
        paused = true;
        System.out.println("Pause enabled.");
        return true;
    }

    public boolean stop() {

        return true;
    }
}
