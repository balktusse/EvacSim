public class DataCollector implements IDataCollector {

    private double time;
    private int evacuatedAgents;

    private final StringBuilder reportLog;

    public DataCollector() {
        this.time = 0.0;
        this.evacuatedAgents = 0;
        this.reportLog = new StringBuilder();
        reportLog.append("Time\tEvacuatedAgents\n");
    }

    @Override
    public void collect_data(int evacuatedCount) {
        this.evacuatedAgents += evacuatedCount;
        reportLog.append(String.format("%.1f\t%d\n", time, evacuatedAgents));
    }

    @Override
    public String generateReport() {
        StringBuilder fullReport = new StringBuilder(reportLog);
        fullReport.append("\n--- Simulation Summary ---\n");
        fullReport.append(String.format("Total Time: %.1f seconds\n", time));
        fullReport.append(String.format("Total Evacuated Agents: %d\n", evacuatedAgents));
        return fullReport.toString();
    }

    public void advanceTime(double deltaTime) {
        this.time += deltaTime;
    }

    public void reset() {
        this.time = 0.0;
        this.evacuatedAgents = 0;
        reportLog.setLength(0);
        reportLog.append("Time\tEvacuatedAgents\n");
    }

    public int getTotalEvacuated() {
        return evacuatedAgents;
    }

    public double getElapsedTime() {
        return this.time;
    }

}
