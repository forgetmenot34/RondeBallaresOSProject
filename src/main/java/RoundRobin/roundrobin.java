/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RoundRobin;

/**
 *
 * @author ploba
 */
import CoreFunctions.process;
import CoreFunctions.ganttblock;
import CoreFunctions.scheduler;

import java.util.*;

public class roundrobin implements scheduler {
    private List<process> processes;
    private List<ganttblock> ganttChart;
    private int timeQuantum;

    @Override
    public void setProcesses(List<process> processes) {
        this.processes = new ArrayList<>();
        for (process p : processes) {
            p.remainingTime = p.burstTime;
            p.startTime = -1; // unset
            this.processes.add(p);
        }
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void simulate() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter time quantum for Round Robin: ");
        timeQuantum = scanner.nextInt();

        int currentTime = 0;
        Queue<process> queue = new LinkedList<>();
        List<process> arrived = new ArrayList<>();
        Set<process> finished = new HashSet<>();

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int index = 0;

        while (finished.size() < processes.size()) {
            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                queue.add(processes.get(index));
                arrived.add(processes.get(index));
                index++;
            }

            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }

            process current = queue.poll();

            if (current.startTime == -1) {
                current.startTime = currentTime;
            }

            int execTime = Math.min(current.remainingTime, timeQuantum);
            ganttChart.add(new ganttblock(current.id, currentTime, currentTime + execTime));

            currentTime += execTime;
            current.remainingTime -= execTime;

            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                if (!arrived.contains(processes.get(index))) {
                    queue.add(processes.get(index));
                    arrived.add(processes.get(index));
                }
                index++;
            }

            if (current.remainingTime > 0) {
                queue.add(current);
            } else {
                current.finishTime = currentTime;
                current.turnaroundTime = current.finishTime - current.arrivalTime;
                current.responseTime = current.startTime - current.arrivalTime;
                finished.add(current);
            }
        }
    }

    @Override
    public List<ganttblock> getGanttChart() {
        return ganttChart;
    }

    @Override
    public List<process> getResultProcesses() {
        return processes;
    }

    @Override
    public String getName() {
        return "Round Robin (RR)";
    }
}
