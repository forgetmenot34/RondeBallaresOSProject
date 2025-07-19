import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import CoreFunctions.process;
import CoreFunctions.scheduler;
import CoreFunctions.ganttblock;

import FirstComeFirstServe.fcfs;
import ShortestJobFirst.sjf;
import ShortestRemainingTimeFirst.srtf;
import RoundRobin.roundrobin;
import MultiLevelFeedbackQueue.mlfq;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SchedulerGUI extends JFrame {
    private JComboBox<String> algorithmCombo;
    private DefaultTableModel processTableModel;
    private JTextArea outputArea;
    private GanttChartPanel ganttPanel;

    public SchedulerGUI() {
        setTitle("CPU Scheduling Simulator");
        setSize(900, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(40, 75, 99));

        algorithmCombo = new JComboBox<>(new String[]{
            "First Come First Serve (FCFS)",
            "Shortest Job First (SJF)",
            "Shortest Remaining Time First (SRTF)",
            "Round Robin (RR)",
            "Multi-Level Feedback Queue (MLFQ)"
        });

        JButton addButton = new JButton("Add Process");
        JButton simulateButton = new JButton("Simulate");
        JButton clearButton = new JButton("Clear");

        algorithmCombo.setBackground(Color.WHITE);
        algorithmCombo.setForeground(Color.BLACK);
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        simulateButton.setBackground(new Color(33, 150, 243));
        simulateButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(244, 67, 54));
        clearButton.setForeground(Color.WHITE);

        JLabel algorithmLabel = new JLabel("Select Algorithm:");
        algorithmLabel.setForeground(Color.WHITE);

        topPanel.add(algorithmLabel);
        topPanel.add(algorithmCombo);
        topPanel.add(addButton);
        topPanel.add(simulateButton);
        topPanel.add(clearButton);
        add(topPanel, BorderLayout.NORTH);

        processTableModel = new DefaultTableModel(new Object[]{"Process ID", "Arrival Time", "Burst Time"}, 0);
        JTable processTable = new JTable(processTableModel);
        processTable.setFillsViewportHeight(true);
        JScrollPane tableScroll = new JScrollPane(processTable);
        add(tableScroll, BorderLayout.CENTER);

        ganttPanel = new GanttChartPanel();
        JScrollPane ganttScroll = new JScrollPane(ganttPanel);
        ganttScroll.setPreferredSize(new Dimension(900, 120));
        ganttScroll.setBorder(BorderFactory.createTitledBorder("Gantt Chart"));

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setEditable(false);
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(new Color(0, 255, 128));
        outputArea.setRows(10);
        outputArea.setColumns(50);

        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setPreferredSize(new Dimension(800, 220));
        outputScroll.setBorder(BorderFactory.createTitledBorder("Simulation Results"));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(ganttScroll, BorderLayout.NORTH);
        bottomPanel.add(outputScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            int row = processTableModel.getRowCount();
            processTableModel.addRow(new Object[]{"P" + (row + 1), 0, 1});
        });

        simulateButton.addActionListener(e -> runSimulation());

        clearButton.addActionListener(e -> {
            processTableModel.setRowCount(0);
            outputArea.setText("");
            ganttPanel.setGanttChart(new ArrayList<>());
        });

        setVisible(true);
    }

    private void runSimulation() {
        List<process> processes = new ArrayList<>();

        try {
            for (int i = 0; i < processTableModel.getRowCount(); i++) {
                String id = processTableModel.getValueAt(i, 0).toString();
                String atStr = processTableModel.getValueAt(i, 1).toString().trim().replaceFirst("^0+(?!$)", "");
                String btStr = processTableModel.getValueAt(i, 2).toString().trim().replaceFirst("^0+(?!$)", "");

                int at = Integer.parseInt(atStr);
                int bt = Integer.parseInt(btStr);

                processes.add(new process(id, at, bt));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Arrival and Burst times must be valid integers.");
            return;
        }

        if (processes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one process.");
            return;
        }

        scheduler sched;
        int selected = algorithmCombo.getSelectedIndex();

        switch (selected) {
            case 0 -> sched = new fcfs();
            case 1 -> sched = new sjf();
            case 2 -> sched = new srtf();
            case 3 -> {
                sched = new roundrobin();
                String tqStr = JOptionPane.showInputDialog(this, "Enter Time Quantum:", "1");

                if (tqStr == null || tqStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Time Quantum is required for Round Robin.");
                    return;
                }

                try {
                    int tq = Integer.parseInt(tqStr.trim());
                    if (tq <= 0) {
                        JOptionPane.showMessageDialog(this, "Time Quantum must be a positive integer.");
                        return;
                    }
                    for (process p : processes) p.timeQuantum = tq;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid Time Quantum.");
                    return;
                }
            }
 case 4 -> {
    mlfq mlfqScheduler = new mlfq();
    sched = mlfqScheduler;

    String tqInput = JOptionPane.showInputDialog(this,
        "Enter time quanta for Q0, Q1, Q2, Q3 (comma-separated):", "4,8,12,1000");
    if (tqInput == null || tqInput.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Time quanta required.");
        return;
    }

    String allotmentInput = JOptionPane.showInputDialog(this,
        "Enter time allotment per queue level:", "6");
    if (allotmentInput == null || allotmentInput.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Time allotment required.");
        return;
    }

    try {
        int[] quanta = Arrays.stream(tqInput.split(","))
            .map(String::trim).mapToInt(Integer::parseInt).toArray();
        if (quanta.length != 4) {
            JOptionPane.showMessageDialog(this, "Please enter exactly 4 comma-separated values.");
            return;
        }
        int allotment = Integer.parseInt(allotmentInput.trim());

        mlfqScheduler.setConfig(quanta, allotment);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid input. Use integers only.");
        return;
    }
}

            default -> sched = new fcfs();
        }

        sched.setProcesses(processes);
        sched.simulate();

        List<ganttblock> gantt = sched.getGanttChart();
        List<process> result = sched.getResultProcesses();

        StringBuilder sb = new StringBuilder();
        sb.append("Gantt Chart:\n|");
        for (ganttblock b : gantt) {
            sb.append("  ").append(b.processId).append("  |");
        }
        sb.append("\n");

        for (ganttblock b : gantt) {
            sb.append(b.startTime).append("     ");
        }
        sb.append(gantt.get(gantt.size() - 1).endTime).append("\n\n");

        sb.append(String.format("%-10s%-15s%-15s%-18s%-18s%-18s\n",
                "Process", "Arrival Time", "Burst Time", "Completion Time", "Turnaround Time", "Response Time"));

        double totalTAT = 0;
        double totalRT = 0;
        for (process p : result) {
            int tat = p.finishTime - p.arrivalTime;
            int rt = p.startTime - p.arrivalTime;
            totalTAT += tat;
            totalRT += rt;

            sb.append(String.format("%-10s%-15d%-15d%-18d%-18d%-18d\n",
                    p.id, p.arrivalTime, p.burstTime, p.finishTime, tat, rt));
        }

        sb.append(String.format("\nAverage Turnaround Time: %.2f\n", totalTAT / result.size()));
        sb.append(String.format("Average Response Time: %.2f\n", totalRT / result.size()));

        outputArea.setText(sb.toString());
        ganttPanel.setGanttChart(gantt);
    }

    class GanttChartPanel extends JPanel {
        private List<ganttblock> chart;

        public void setGanttChart(List<ganttblock> chart) {
            this.chart = chart;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (chart == null || chart.isEmpty()) return;

            int blockWidth = 50;
            int height = 40;
            int x = 20;
            int y = 30;

            for (ganttblock block : chart) {
                int width = blockWidth * (block.endTime - block.startTime);

                g.setColor(new Color(173, 216, 230));
                g.fillRect(x, y, width, height);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, width, height);
                g.drawString(block.processId, x + width / 2 - 10, y + 25);
                g.drawString(String.valueOf(block.startTime), x - 5, y + height + 20);

                x += width;
            }

            if (!chart.isEmpty()) {
                ganttblock last = chart.get(chart.size() - 1);
                g.drawString(String.valueOf(last.endTime), x - 5, y + height + 20);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            if (chart == null || chart.isEmpty()) {
                return new Dimension(1000, 100);
            }

            int blockWidth = 50;
            int totalWidth = 0;

            for (ganttblock block : chart) {
                totalWidth += blockWidth * (block.endTime - block.startTime);
            }

            return new Dimension(Math.max(1000, totalWidth + 40), 100);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SchedulerGUI::new);
    }
}
