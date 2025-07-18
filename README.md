Java CPU Scheduler Simulator

Ballares, Dan Paolo B.

Ronde, Richie Noel O.


A Java-based CPU Scheduling Simulator with a functional Swing GUI frontend and 
a complete backend implementation of multiple scheduling algorithms. 
Designed for educational use in Operating Systems course.

---

Features:

Multiple Scheduling Algorithms:

First Come First Serve (FCFS)
    -An algorithm where processes are executed in the order they arrive, similar to people lining up in a queue.
      No prioritization or preemption.

Shortest Job First (SJF)
    - An algorithm where the CPU selects the process with the shortest total burst time (execution time).
      It’s non-preemptive, once a process starts, it runs until it finishes.

Shortest Remaining Time First (SRTF)
    - The CPU always picks the process with the shortest time left to finish.
      If a new process arrives with a shorter remaining time, it interrupts the current one.


Round Robin (RR)
    - Each process gets a fixed time slice (quantum) to run. If it doesn’t finish in time, 
      it’s put back in the queue, and the next process runs.
      Fair and time-sharing focused.

Multi-Level Feedback Queue (MLFQ)
    - Processes are assigned to multiple queues with different priorities.
      If a process uses too much CPU time, it’s demoted to a lower-priority queue.
      It adapts over time and balances responsiveness and fairness.

Java Swing GUI:

  - Visual interface for running scheduling algorithms
  - Real-time simulation with output display
  - Easy input for process data and time quantum

 Detailed Metrics Output:

- Gantt Chart (ASCII-style)
- Per-process metrics:
    - Arrival Time
    - Burst Time
    - Completion Time
    - Turnaround Time
    - Waiting Time
    - Response Time
  - Averages for each metric

- 💡 **Configurable Parameters**:
  - Random or manual process generation
  - Custom time quantums for RR and MLFQ

---

Project Structure

Backend/
├── src/
│ └── main/java/
│ ├── CoreFunctions/ # Main scheduler logic and shared utilities
│ ├── FirstComeFirstServe/ # FCFS implementation
│ ├── RoundRobin/ # RR implementation
│ ├── ShortestJobFirst/ # SJF implementation
│ ├── ShortestRemainingTimeFirst/ # SRTF implementation
│ └── MultiLevelFeedbackQueue/ # MLFQ implementation
|
|___SchedulerGUI.java # Java Swing GUI front end


CoreFunctions.main:

A driver code for the backend code, located in main, to ensure flow of algorithms is 
correct, interconnected properly using imports, and running well, also has a error-handling feature.

Screen shots of run of backend codes using driver code in main:

FCFS:

![fcfs w error 1](https://github.com/user-attachments/assets/95273ce2-a97b-45db-b092-197534eb6bf3)
![fcfs w error 2](https://github.com/user-attachments/assets/fdc80374-2190-4694-8484-d2b80357f9db)

SJF:

![SJF sample input](https://github.com/user-attachments/assets/5ccb0fec-9500-4982-86ae-e0db56be15fa)

SRTF:

![srtf sample code 1](https://github.com/user-attachments/assets/25431db7-7938-448f-978c-12099a6fa80a)
![srtf sample code 2](https://github.com/user-attachments/assets/38dbd89f-4ea9-443f-9568-0ea51910949f)

RR:

![ROund Robin 1](https://github.com/user-attachments/assets/baad0d14-2aea-41e7-9769-bfd12afb2270)
![ROund Robin 2](https://github.com/user-attachments/assets/fdadee8b-3246-4c47-9117-51ca6376817c)

MLFQ:

![MLFQ sample](https://github.com/user-attachments/assets/a8349514-0fd3-4ff9-98d4-adf8f3dcb712)


Issues with code:

When entering Round Robin in the GUI, after entering time quantum in the GUI itself, it goes back to the terminal and asks for the time quantum the user just entered. 
Other than that, the code works fine.

![Round Robin issues](https://github.com/user-attachments/assets/916ed9a3-b167-437c-872f-7bf127bfa360)

Roles:
Richie Ronde: Front End

Dan Ballares: Back End

