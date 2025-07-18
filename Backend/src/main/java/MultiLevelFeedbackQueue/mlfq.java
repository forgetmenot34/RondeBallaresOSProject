/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MultiLevelFeedbackQueue;

/**
 *
 * @author ploba
 */
import CoreFunctions.ganttblock;
import CoreFunctions.process;
import CoreFunctions.scheduler;

import java.util.*;

public class mlfq implements scheduler {
    private List<process> allProcesses;
    private List<ganttblock> ganttChart = new ArrayList<>();
    private List<process> completed = new ArrayList<>();

    // 4 Queues: Q0 to Q3 (Q0 highest priority)
    private final List<Queue<process>> queues = Arrays.asList(
            new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());

    private int[] timeQuanta = {4, 8, 12, Integer.MAX_VALUE}; // Round robin for Q0 to Q2, FCFS for Q3
    private int currentTime = 0;

    @Override
    public void setProcesses(List<process> processes) {
        this.allProcesses = new ArrayList<>();
        for (process p : processes) {
            p.remainingTime = p.burstTime;
            allProcesses.add(p);
        }
    }

    @Override
    public void simulate() {
        allProcesses.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int n = allProcesses.size();
        int arrived = 0;

        while (completed.size() < n) {
            // Add newly arrived processes to Q0
            while (arrived < n && allProcesses.get(arrived).arrivalTime <= currentTime) {
                queues.get(0).offer(allProcesses.get(arrived));
                arrived++;
            }

            process current = null;
            int level = -1;

            // Pick the first non-empty queue
            for (int i = 0; i < queues.size(); i++) {
                if (!queues.get(i).isEmpty()) {
                    current = queues.get(i).poll();
                    level = i;
                    break;
                }
            }

            if (current == null) {
                currentTime++;
                continue;
            }

            if (current.startTime == 0 && current.remainingTime == current.burstTime) {
                current.startTime = Math.max(currentTime, current.arrivalTime);
            }

            int execTime = Math.min(timeQuanta[level], current.remainingTime);
            ganttChart.add(new ganttblock(current.id + "(Q" + level + ")", currentTime, currentTime + execTime));

            currentTime += execTime;
            current.remainingTime -= execTime;

            // Check for newly arrived processes during execution
            while (arrived < n && allProcesses.get(arrived).arrivalTime <= currentTime) {
                queues.get(0).offer(allProcesses.get(arrived));
                arrived++;
            }

            if (current.remainingTime == 0) {
                current.finishTime = currentTime;
                completed.add(current);
            } else {
                // Demote if not in Q3
                if (level < 3) queues.get(level + 1).offer(current);
                else queues.get(3).offer(current); // Stay in Q3
            }
        }
    }

    @Override
    public List<ganttblock> getGanttChart() {
        return ganttChart;
    }

    @Override
    public List<process> getResultProcesses() {
        return completed;
    }

    @Override
    public String getName() {
        return "Multilevel Feedback Queue (MLFQ)";
    }
}

