/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FirstComeFirstServe;

/**
 *
 * @author ploba
 */
import FirstComeFirstServe.process;
import FirstComeFirstServe.ganttblock;
import java.util.List;

public interface scheduler {
    void setProcesses(List<process> processes);
    void simulate();
    List<ganttblock> getGanttChart();
    List<process> getResultProcesses();
    String getName();
}