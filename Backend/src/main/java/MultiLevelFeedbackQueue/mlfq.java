package MultiLevelFeedbackQueue;

import CoreFunctions.ganttblock;
import CoreFunctions.process;
import CoreFunctions.scheduler;

import java.util.*;

public class mlfq implements scheduler {
    private List<process> allProcesses;
    private List<ganttblock> ganttChart = new ArrayList<>();
    private List<process> completed = new ArrayList<>();
    private List<Queue<process>> queues = Arrays.asList(
            new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());

    private int[] timeQuanta = new int[4]; // e.g., {4, 8, 12, 1000}
    private int timeAllotment = 6;

    public void setConfig(int[] quanta, int allotment) {
        if (quanta == null || quanta.length != 4) {
            throw new IllegalArgumentException("You must provide exactly 4 time quanta values.");
        }
        this.timeQuanta = quanta;
        this.timeAllotment = allotment;
    }

    @Override
    public void setProcesses(List<process> processes) {
        allProcesses = new ArrayList<>();
        for (process p : processes) {
            p.remainingTime = p.burstTime;
            p.level = 0;
            p.allotted = 0;
            p.startTime = -1; // Indicates not yet started
            allProcesses.add(p);
        }
    }

    @Override
    public void simulate() {
        allProcesses.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int n = allProcesses.size();
        int arrived = 0;

        int currentTime = allProcesses.isEmpty() ? 0 : allProcesses.get(0).arrivalTime;

        while (completed.size() < n) {
            // Add newly arrived processes to Q0
            while (arrived < n && allProcesses.get(arrived).arrivalTime <= currentTime) {
                queues.get(0).offer(allProcesses.get(arrived));
                arrived++;
            }

            process current = null;
            int level = -1;

            // Find next process to run
            for (int i = 0; i < 4; i++) {
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

            if (current.startTime == -1) {
                current.startTime = currentTime;
            }

            int tq = timeQuanta[level];
            int allowed = Math.min(tq, timeAllotment - current.allotted);
            int execTime = Math.min(allowed, current.remainingTime);

            ganttChart.add(new ganttblock(current.id + "(Q" + level + ")", currentTime, currentTime + execTime));

            current.remainingTime -= execTime;
            current.allotted += execTime;
            currentTime += execTime;

            // Add newly arrived during execution
            while (arrived < n && allProcesses.get(arrived).arrivalTime <= currentTime) {
                queues.get(0).offer(allProcesses.get(arrived));
                arrived++;
            }

            if (current.remainingTime == 0) {
                current.finishTime = currentTime;
                completed.add(current);
            } else {
                if (current.allotted >= timeAllotment && level < 3) {
                    current.level = level + 1;
                    current.allotted = 0;
                    queues.get(level + 1).offer(current);
                } else {
                    queues.get(level).offer(current);
                }
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
