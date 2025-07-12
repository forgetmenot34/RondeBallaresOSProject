/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rondeballaresosproject;

/**
 *
 * @author ploba
 */
public class ganttblock {
     public String processId;
    public int startTime;
    public int endTime;

    public GanttBlock(String processId, int startTime, int endTime) {
        this.processId = processId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[" + processId + ": " + startTime + "-" + endTime + "]";
    }
}
