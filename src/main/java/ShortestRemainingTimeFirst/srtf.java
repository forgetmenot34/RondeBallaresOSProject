/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShortestRemainingTimeFirst;

/**
 *
 * @author ploba
 */
import CoreFunctions.ganttblock;
import CoreFunctions.scheduler;
import CoreFunctions.process;

import java.util.*;

public class srtf implements scheduler 
{
    private List<process> processList;
    private List<ganttblock> ganttChart;

    public srtf() 
    {
        this.ganttChart = new ArrayList<>();
    }

    @Override
    public void setProcesses(List<process> processes) 
    {
        this.processList = new ArrayList<>();
        for (process p : processes) {
            
            this.processList.add(new process(p.id, p.arrivalTime, p.burstTime));
        }
    }

    @Override
    public void simulate() 
    {
        ganttChart.clear();
        int currentTime = 0;
        int completed = 0;
        int n = processList.size();
        int[] remainingTime = new int[n];
        boolean[] isStarted = new boolean[n];
        boolean[] isCompleted = new boolean[n];

        for (int i = 0; i < n; i++) 
        {
            remainingTime[i] = processList.get(i).burstTime;
        }

        String currentProcessId = null;
        int lastSwitchTime = 0;

        while (completed < n) 
        {
            int idx = -1;
            int minRemaining = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) 
            {
                process p = processList.get(i);
                if (p.arrivalTime <= currentTime && !isCompleted[i] && remainingTime[i] < minRemaining && remainingTime[i] > 0) 
                {
                    minRemaining = remainingTime[i];
                    idx = i;
                }
            }

            if (idx != -1) 
            {
                process p = processList.get(idx);

                if (!isStarted[idx]) 
                {
                    p.startTime = currentTime;
                    isStarted[idx] = true;
                }

                
                if (currentProcessId == null || !currentProcessId.equals(p.id)) 
                {
                    if (currentProcessId != null) 
                    {
                        ganttChart.add(new ganttblock(currentProcessId, lastSwitchTime, currentTime));
                    }
                    currentProcessId = p.id;
                    lastSwitchTime = currentTime;
                }

                remainingTime[idx]--;
                currentTime++;

                if (remainingTime[idx] == 0) 
                {
                    p.finishTime = currentTime;
                    isCompleted[idx] = true;
                    completed++;
                }
            } else {
                
                if (currentProcessId != null) 
                {
                    ganttChart.add(new ganttblock(currentProcessId, lastSwitchTime, currentTime));
                    currentProcessId = null;
                }
                currentTime++;
            }
        }

        if (currentProcessId != null) 
        {
            ganttChart.add(new ganttblock(currentProcessId, lastSwitchTime, currentTime));
        }
    }

    @Override
    public List<ganttblock> getGanttChart() 
    {
        return ganttChart;
    }

    @Override
    public List<process> getResultProcesses()
    {
        return processList;
    }

    @Override
    public String getName() 
    {
        return "Shortest Remaining Time First (Preemptive)";
    }
}

