/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoreFunctions;

/**
 *
 * @author ploba
 */

// SCHEDULER CODE

import CoreFunctions.process;
import java.util.List;

public interface scheduler {
    void setProcesses(List<process> processes);
    void simulate();
    List<ganttblock> getGanttChart();
    List<process> getResultProcesses();
    String getName();
}