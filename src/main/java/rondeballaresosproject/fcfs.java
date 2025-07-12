/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rondeballaresosproject;

/**
 *
 * @author ploba
 */
import Model.Process;
import Model.GanttBlock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCFS implements Scheduler {
    private List<Process> processList;
    private List<GanttBlock> ganttChart;

    public FCFS() {
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void setProcesses(List<Process> processes) {
        this.processList = new ArrayList<>(processes);
        this.processList.sort(Comparator.comparingInt(p -> p.arrivalTime));
    }

    @Override
    public void simulate() {
        ganttChart.clear();
        int currentTime = 0;

        for (Process p : processList) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }

            p.startTime = currentTime;
            p.finishTime = currentTime + p.burstTime;
            p.waitingTime = p.startTime - p.arrivalTime;
            p.turnaroundTime = p.finishTime - p.arrivalTime;

            ganttChart.add(new GanttBlock(p.id, p.startTime, p.finishTime));
            currentTime += p.burstTime;
        }
    }

    @Override
    public List<GanttBlock> getGanttChart() {
        return ganttChart;
    }

    @Override
    public List<Process> getResultProcesses() {
        return processList;
    }

    @Override
    public String getName() {
        return "First-Come First-Serve (FCFS)";
    }
}