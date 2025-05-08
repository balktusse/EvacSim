public class Simulator {

    private Environment environment;
    private DataCollector data_collector;

    public Simulator(Environment environment, DataCollector data_collector) {
        this.environment = environment;
        this.data_collector = data_collector;
    }

    public boolean run() {

        return true;
    }

    public boolean update() {
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

        return true;
    }

    public boolean stop() {

        return true;
    }
}
