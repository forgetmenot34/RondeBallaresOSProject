/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package rondeballaresosproject;

/**
 *
 * @author ploba
 */


import rondeballaresosproject.fcfs;
import rondeballaresosproject.process;
import java.util.Arrays;
import java.util.List;

public class Main {
        public static void main(String[] args) {
        List<process> processes = Arrays.asList(
                new process("P1", 0, 4) {},
                new process("P2", 1, 3),
                new process("P3", 2, 1),
                new process("P4", 3, 2)
        );

        scheduler scheduler = new fcfs();
        scheduler.setProcesses(processes);
        scheduler.simulate();

        System.out.println("Gantt Chart:");
        for (ganttblock block : scheduler.getGanttChart()) {
            System.out.println(block);
        }

        System.out.println("\nProcess Details:");
        for (process p : scheduler.getResultProcesses()) {
            System.out.println(p);
        }
    }
}
