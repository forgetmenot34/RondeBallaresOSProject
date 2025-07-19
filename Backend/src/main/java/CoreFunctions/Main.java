package CoreFunctions;

public class process {
    public String id;
    public int arrivalTime;
    public int burstTime;
    public int remainingTime;
    public int finishTime;
    public int startTime;
    public int timeQuantum;

    public int level;     // current queue level
    public int allotted;  // time consumed at current level

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
