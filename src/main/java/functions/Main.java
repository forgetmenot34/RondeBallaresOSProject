/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package functions;

/**
 *
 * @author ploba
 */


import FirstComeFirstServe.fcfs;
import functions.ganttblock;
import functions.scheduler;

import ShortestJobFirst.sjf;
import functions.process;
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
            System.out.println(block);
        }

        System.out.println("\nProcess Details:");
        for (process p : scheduler.getResultProcesses()) {
            System.out.println(p);
        }

        scanner.close();
    }
}
