/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoreFunctions;

/**
 *
 * @author ploba
 */
import FirstComeFirstServe.fcfs;
import CoreFunctions.process;

public class process {
    public String id;
    public int arrivalTime;
    public int burstTime;
    public int startTime;
    public int finishTime;
    public int waitingTime;
    public int turnaroundTime;
    public int responseTime;

    public process(String id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    @Override
    public String toString() {
        return String.format("Process %s: AT=%d, BT=%d, ST=%d, FT=%d, WT=%d, TAT=%d",
                id, arrivalTime, burstTime, startTime, finishTime, waitingTime, turnaroundTime);
    }
}
