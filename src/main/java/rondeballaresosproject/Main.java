/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package rondeballaresosproject;

/**
 *
 * @author ploba
 */



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
public class Main {

    public static void main(String[] args) {
        
   public static void main(String[] args) {
        List<Process> processes = Arrays.asList(
                new Process("P1", 0, 4) {},
                new Process("P2", 1, 3),
                new Process("P3", 2, 1),
                new Process("P4", 3, 2)
        );

        Scheduler scheduler = new FCFS();
        scheduler.setProcesses(processes);
        scheduler.simulate();

        System.out.println("Gantt Chart:");
        for (GanttBlock block : scheduler.getGanttChart()) {
            System.out.println(block);
        }

        System.out.println("\nProcess Details:");
        for (Process p : scheduler.getResultProcesses()) {
            System.out.println(p);
        }
    }
}
