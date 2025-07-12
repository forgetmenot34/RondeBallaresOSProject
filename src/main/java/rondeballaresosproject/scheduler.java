/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rondeballaresosproject;

/**
 *
 * @author ploba
 */
import rondeballaresosproject.process;
import rondeballaresosproject.ganttblock;
import java.util.List;

public interface scheduler {
    void setProcesses(List<Process> processes);
    void simulate();
    List<ganttblock> getGanttChart();
    List<Process> getResultProcesses();
    String getName();
}