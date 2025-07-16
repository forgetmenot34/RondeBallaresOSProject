/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package CoreFunctions;

/**
 *
 * @author ploba
 */


import FirstComeFirstServe.fcfs;
import CoreFunctions.ganttblock;
import CoreFunctions.scheduler;

import ShortestJobFirst.sjf;
import CoreFunctions.process;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
          public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose Scheduling Algorithm:");
        System.out.println("1. First Come First Serve (FCFS)");
        System.out.println("2. Shortest Job First (SJF)");
        System.out.print("Enter choice (1 or 2): ");
        int choice = scanner.nextInt();

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();

        List<process> processes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("\nProcess P" + (i + 1));
            System.out.print("Arrival Time: ");
            int at = scanner.nextInt();
            System.out.print("Burst Time: ");
            int bt = scanner.nextInt();
            processes.add(new process("P" + (i + 1), at, bt));
        }

        scheduler scheduler;

        switch (choice) {
            case 1:
                scheduler = new fcfs();
                break;
            case 2:
                scheduler = new sjf();
                break;
            default:
                System.out.println("Invalid choice. Defaulting to FCFS.");
                scheduler = new fcfs();
        }

        scheduler.setProcesses(processes);
        scheduler.simulate();

        System.out.println("\nGantt Chart:");
        for (ganttblock block : scheduler.getGanttChart()) {
            System.out.print("|  " + block.processId + "  ");
        }
        System.out.println("|");

        for (ganttblock block : scheduler.getGanttChart()) {
            System.out.print(block.startTime + "     ");
        }
        System.out.println(scheduler.getGanttChart().get(scheduler.getGanttChart().size() - 1).endTime);

        System.out.printf("\n%-10s%-15s%-15s%-18s%-18s%-18s\n",
                "Process", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Response Time");

        double totalTAT = 0;
        double totalRT = 0;

        for (process p : scheduler.getResultProcesses()) {
            int rt = p.startTime - p.arrivalTime;
            int tat = p.finishTime - p.arrivalTime;
            totalTAT += tat;
            totalRT += rt;
            System.out.printf("%-10s%-15d%-15d%-18d%-18d%-18d\n",
                    p.id, p.arrivalTime, p.burstTime, p.finishTime, tat, rt);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", totalTAT / n);
        System.out.printf("Average Response Time: %.2f\n", totalRT / n);

        scanner.close();
    }
}
