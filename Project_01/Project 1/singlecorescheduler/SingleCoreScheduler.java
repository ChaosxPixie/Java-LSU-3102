package singlecorescheduler;

/**
 * An application to simulate a non-preemptive scheduler for a single-core CPU
 * using a heap-based implementation of a priority queue
 * @author William Duncan, Malana Fuentes
 * @see PQueue.java, PCB.java
 * <pre>
 * DATE: 9/21/22
 * File:SingleCoreScheduler.java
 * Course: csc 3102
 * Programming Project # 1
 * Instructor: Dr. Duncan
 * Usage: SingleCoreScheduler <number of cylces> <-R or -r> <probability of a  process being created per cycle>  or,
 *        SingleCoreScheduler <number of cylces> <-F or -f> <file name of file containing processes>,
 *        The simulator runs in either random (-R or -r) or file (-F or -f) mode
 * </pre>
 */

import java.io.*;
import java.text.DecimalFormat;
import java.util.Random;
import java.io.FileReader;
import java.io.IOException;

public class SingleCoreScheduler
{
    /**
     * Single-core processor with non-preemptive scheduling simulator
     * @param args an array of strings containing command line arguments
     * args[0] - number of cyles to run the simulation
     * args[1] - the mode: -r or -R for random mode and -f or -F for file mode
     * args[2] - if the mode is random, this entry contains the probability that
     * a process is created per cycle and if the simulator is running in
     *           file mode, this entry contains the name of the file containing
     *           the simulated jobs. In file mode, each line of the input file is
     *           in this format:
     * <process ID> <priority value> <cycle of process creation> <time required to execute>
     */

    private static final String idle = "The CPU is idle.";
    private static final String noJob = "No new job this cycle.";

    public static void main(String []args) throws PQueueException, IOException {
        if (args.length != 3) {
            System.out.println("Usage: SingleCoreScheduler <number of cylces> <-R or " +
                    "-r> <probability of a  process being created per cycle>  or ");
            System.out.println("       SingleCoreScheduler <number of cylces> <-F or " +
                    " -f> <file name of file containing processes>");
            System.out.println("The simulator runs in either random (-R or -r) or file (-F or -f) mode.");
            System.exit(1);
        }


    //Complete the implementation of this method
    //output to a file
        PQueueAPI<PCB> priorityQueue = new PQueue<>();
        int cycles = Integer.parseInt(args[0]);
        String mode = args[1];
        BufferedReader bufferedReader = null;
        double p = 0.0d;
        int prevArrival = 0;
        int pid = 1;
        Random random = new Random(System.currentTimeMillis());
        if (mode.equalsIgnoreCase("-r")) {
            p = Double.parseDouble(args[2]);
        } else if (mode.equalsIgnoreCase("-f")) {
            String fileName = args[2];
            bufferedReader = new BufferedReader(new FileReader(fileName));
        }
        PCB nextJob = null;
        PCB currJob = null;
        double totalProcesses = 0;
        double totalWaitTime = 0;
        for (int i = 0; i <= cycles; i++) {
            printCycleInfo(i);

            if (currJob == null && !priorityQueue.isEmpty()) {
                currJob = priorityQueue.remove();
                currJob.setStart(i);
                currJob.execute();
                currJob.setWait(i - currJob.getArrival());
                totalWaitTime += (i - currJob.getArrival());
            } else if (currJob == null) {
                System.out.println(idle);
            }

            if (currJob != null && (currJob.getStart() + currJob.getBurst()) > i) {
                printProcessExec(currJob);
            } else if (currJob != null && (currJob.getStart() + currJob.getBurst()) == i) {
                printProcessCompl(currJob);
                totalProcesses++;
                currJob = null;
            }

            if (nextJob != null && nextJob.getArrival() == i) {
                priorityQueue.insert(nextJob);
                printJobInfo(nextJob);
                nextJob = null;
            } else {
                System.out.println(noJob);
            }

            if (nextJob == null) {
                if (bufferedReader != null) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        String[] values = line.split(" ");
                        nextJob = new PCB(  Integer.valueOf(values[0]), Integer.valueOf(values[1]), 0, Integer.valueOf(values[2]), Integer.valueOf(values[3]));
                    }
                }else{
                    double q = Math.random();
                    if(q<=p){
                        int arrivalTime = random.nextInt((cycles - prevArrival) + 1) + (prevArrival + 1);
                        int priority = random.nextInt((20 - (-19)) + 1) + (-19);
                        int noOfCycles = random.nextInt(((cycles-arrivalTime) - 1) + 1) + 1;
                        nextJob = new PCB(pid++, priority, 0, arrivalTime, noOfCycles);
                    }
                }
            }
        }

        if (bufferedReader != null) {
            bufferedReader.close();
        }

        System.out.println("The average throughput is " + new DecimalFormat("#.#####").format(totalProcesses / (cycles + 1)) + " per cycle.");
        System.out.println("The average wait time is " + (totalWaitTime / totalProcesses) + ".");

    }

    private static void printCycleInfo(int cycleNo) {
        System.out.println("*** Cycle #: " + cycleNo + " ***");
    }
    private static void printJobInfo(PCB job) {
        System.out.println("Adding job with pid #" + job.getPid() + " and priority " + job.getPriority() + " and length " + job.getBurst() + ".");
    }
    private static void printProcessExec(PCB job) {
        System.out.println("Process #" + job.getPid() + " is executing.");
    }
    private static void printProcessCompl(PCB job) {
        System.out.println("Process #" + job.getPid() + " has just terminated.");
    }
}