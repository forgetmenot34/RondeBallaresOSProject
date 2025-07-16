/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShortestJobFirst;

/**
 *
 * @author ploba
 */

import CoreFunctions.process;
import CoreFunctions.ganttblock;
import CoreFunctions.scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class sjf implements scheduler {
 private List<process> processList;
    private List<ganttblock> ganttChart;

    public sjf(){
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void setProcesses(List<process> processes) {
        this.processList = new ArrayList<>(processes);
    }

    @Override
    public void simulate() {
        ganttChart.clear();
        List<process> completed = new ArrayList<>();
        int currentTime = 0;

        while (completed.size() < processList.size()) {
            
            List<process> available = new ArrayList<>();
            for (process p : processList) {
                if (p.arrivalTime <= currentTime && !completed.contains(p)) {
                    available.add(p);
                }
            }

            if (available.isEmpty()) {
                currentTime++; 
                continue;
            }

            
            process shortest = available.stream()
                    .min(Comparator.comparingInt(p -> p.burstTime))
                    .orElseThrow();

            shortest.startTime = currentTime;
            shortest.finishTime = currentTime + shortest.burstTime;
            shortest.waitingTime = shortest.startTime - shortest.arrivalTime;
            shortest.turnaroundTime = shortest.finishTime - shortest.arrivalTime;

            ganttChart.add(new ganttblock(shortest.id, shortest.startTime, shortest.finishTime));

            currentTime += shortest.burstTime;
            completed.add(shortest);
        }
    }

    @Override
    public List<ganttblock> getGanttChart() {
        return ganttChart;
    }

    @Override
    public List<process> getResultProcesses() {
        return processList;
    }

    @Override
    public String getName() {
        return "Shortest Job First (Non-Preemptive)";
    }
}
