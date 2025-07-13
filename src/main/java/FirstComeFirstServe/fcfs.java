/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FirstComeFirstServe;

/**
 *
 * @author ploba
 */
import FirstComeFirstServe.process;
import FirstComeFirstServe.ganttblock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class fcfs implements scheduler {
    private List<process> processList;
    private List<ganttblock> ganttchart;

    public fcfs() {
        this.ganttchart = new ArrayList<>();
    }

    @Override
    public void setProcesses(List<process> processes) {
        this.processList = new ArrayList<>(processes);
        this.processList.sort(Comparator.comparingInt(p -> p.arrivalTime));
    }

    @Override
    public void simulate() {
        ganttchart.clear();
        int currentTime = 0;

        for (process p : processList) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }

            p.startTime = currentTime;
            p.finishTime = currentTime + p.burstTime;
            p.waitingTime = p.startTime - p.arrivalTime;
            p.turnaroundTime = p.finishTime - p.arrivalTime;

            ganttchart.add(new ganttblock(p.id, p.startTime, p.finishTime));
            currentTime += p.burstTime;
        }
    }

    @Override
    public List<ganttblock> getGanttChart() {
        return ganttchart;
    }

    @Override
    public List<process> getResultProcesses() {
        return processList;
    }

    @Override
    public String getName() {
        return "First-Come First-Serve (FCFS)";
    }
}