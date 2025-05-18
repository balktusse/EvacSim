public class DataCollector implements IDataCollector {

    private double time;
    private int evacuated_agents;

    private final StringBuilder reportLog;

    public DataCollector() {
        this.time = 0.0;
        this.evacuated_agents = 0;
        this.reportLog = new StringBuilder();
        reportLog.append("Time\tEvacuatedAgents\n");
    }

    @Override
    public void collectData(int evacuatedAgents) {
        this.evacuated_agents = evacuatedAgents;
        reportLog.append(String.format("%.1f\t%d\n", time, evacuated_agents));
    }

    @Override
    public String generateReport() {
        return reportLog.toString();
    }

    public void advanceTime(double deltaTime) {
        this.time += deltaTime;
    }
}
