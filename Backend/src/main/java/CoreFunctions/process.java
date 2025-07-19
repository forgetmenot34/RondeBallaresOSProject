package CoreFunctions;

public class process {
    public String id;
    public int arrivalTime;
    public int burstTime;
    public int startTime;
    public int finishTime;
    public int remainingTime;
    public int waitingTime;
    public int turnaroundTime;
    public int timeQuantum;
    public int completionTime;
    public int responseTime;

    public int level;     
    public int allotted; 

    public process(String id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.startTime = -1;
        this.finishTime = 0;
        this.level = 0;
        this.allotted = 0;
    }
}
