/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package CoreFunctions;

/**
 *
 * @author ploba
 */

// MAIN CODE

import FirstComeFirstServe.fcfs;
import CoreFunctions.ganttblock;
import CoreFunctions.scheduler;

import ShortestJobFirst.sjf;
import CoreFunctions.process;
import RoundRobin.roundrobin;
import ShortestRemainingTimeFirst.srtf;
import MultiLevelFeedbackQueue.mlfq;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        int n = 0;

        // Get algorithm choice with error handling
        while (true) 
        {
            try 
            {
                System.out.println("Choose Scheduling Algorithm:");
                System.out.println("1. First Come First Serve (FCFS)");
                System.out.println("2. Shortest Job First (SJF)");
                System.out.println("3. Shortest Remaining Time First (SRTF)");
                System.out.println("4. Round Robin (RR)");
                System.out.println("5. Multi-level Feedback Queue (MLFQ)");
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();

                if (choice < 1 || choice > 5)
                {
                    System.out.println("\nMessage:\n\nINVALID CHOICE. PLEASE ENTER A NUMBER FROM 1 TO 4.\n\n");
                    continue;
                }
                break;
            }
             catch (InputMismatchException e)    
            {
                System.out.println("\nMessage:\n\nINVALID INPUT. PLEASE ENTER A NUMBER FROM 1 TO 4.\n\n");
                scanner.next(); // clear invalid input
            }
        }

        // Get number of processes
        while (true)
        {
            try 
            {
                System.out.print("Enter number of processes: ");
                n = scanner.nextInt();
                if (n <= 0) 
                {
                    System.out.println("Number of processes must be positive.\n");
                    continue;
                }
                break;
            } 
             catch (InputMismatchException e) 
            {
                System.out.println("Invalid input. Please enter a valid integer.\n");
                scanner.next(); // clear invalid input
            }
        }

        List<process> processes = new ArrayList<>();
        for (int i = 0; i < n; i++) 
        {
            int at = 0, bt = 0;
            System.out.println("\nProcess P" + (i + 1));

            // Get Arrival Time
            while (true) 
            {
                try {
                    System.out.print("Arrival Time: ");
                    at = scanner.nextInt();
                    if (at < 0) {
                        System.out.println("Arrival time must be non-negative.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next();
                }
            }

            // Get Burst Time
            while (true) {
                try {
                    System.out.print("Burst Time: ");
                    bt = scanner.nextInt();
                    if (bt <= 0) {
                        System.out.println("Burst time must be positive.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next();
                }
            }

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
            case 3:
                scheduler = new srtf();
                break;
            case 4:
                scheduler = new roundrobin();
                break;
            case 5:
                scheduler = new mlfq();
                break;
            default:
                System.out.println("Invalid choice. Defaulting to FCFS.");
                scheduler = new fcfs();
        }

        scheduler.setProcesses(processes);
        scheduler.simulate();

        System.out.println("\nGantt Chart:");
        for (ganttblock block : scheduler.getGanttChart()) 
        {
            System.out.print("|  " + block.processId + "  ");
        }
        System.out.println("|");

        for (ganttblock block : scheduler.getGanttChart()) 
        {
            System.out.print(block.startTime + "     ");
        }
        System.out.println(scheduler.getGanttChart().get(scheduler.getGanttChart().size() - 1).endTime);

        System.out.printf("\n%-10s%-15s%-15s%-18s%-18s%-18s\n",
                "Process", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Response Time");

        double totalTAT = 0;
        double totalRT = 0;

        for (process p : scheduler.getResultProcesses()) 
        {
            int tat = p.finishTime - p.arrivalTime;
            int rt = p.startTime - p.arrivalTime;
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
