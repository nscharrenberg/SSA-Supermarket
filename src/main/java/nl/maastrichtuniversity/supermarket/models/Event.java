package nl.maastrichtuniversity.supermarket.models;

import nl.maastrichtuniversity.supermarket.contracts.Process;

/**
 * Event Class
 * Events that facilitate changes in the simulation
 * @author Joel Karel
 * @author Noah Scharrenberg
 */
public class Event {
    /**
     * The object involved with the event
     */
    private Process target;

    /**
     * The type of event
     */
    private int type;

    /**
     * The time on which the event will be executed
     */
    private double executionTime;


    /**
     * Constructor for objects
     * @param target The object that will process the event
     * @param type The type of the event
     * @param executionTime The time on which the event will be executed
     */
    public Event(Process target, int type, double executionTime) {
        this.target = target;
        this.type = type;
        this.executionTime = executionTime;
    }

    /**
     * Method to signal the target to execute an event
     */
    public void execute() {
        target.execute(type, executionTime);
    }

    /**
     * Method to ask the event at which time it will be executed
     * @return The time at which the event will be executed
     */
    public double getExecutionTime() {
        return executionTime;
    }
}
