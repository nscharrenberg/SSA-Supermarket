package nl.maastrichtuniversity.supermarket.models;

import nl.maastrichtuniversity.supermarket.contracts.Process;

import java.util.ArrayList;
import java.util.List;

/**
 * Event Processing Mechanism
 * Events are created here and it is ensured that they are processed in the proper order
 * The simulation clock is located here.
 * @author Joel Karel
 * @author Noah Scharrenberg
 */
public class EventList implements Process {
    private double currentTime;
    private final List<Event> events;
    private boolean stopFlag;

    /**
     * Standard Constructor
     * Create an EventList Object
     */
    public EventList() {
        this.currentTime = 0;
        this.stopFlag = false;
        this.events = new ArrayList<>();
    }

    @Override
    public void execute(int type, double time) {
        if (type == -1) {
            stop();
        }
    }

    public void add(Process target, int type, double time) {
        boolean success = false;

        // Create a new event using the parameters
        Event event = new Event(target, type, time);

        // Examine where the event has to be inserted in the list
        for (int i = 0; i < events.size(); i++) {
            // Chronologically sort events
            if (events.get(i).getExecutionTime() > event.getExecutionTime()) {
                // If an event is found in the list that has to be executed after the current event
                success = true;

                // Then the new event is inserted before that element
                events.add(i, event);
                break;
            }
        }

        if (!success) {
            // Else the new event is appended to the list
            events.add(event);
        }
    }

    /**
     * Method for starting the eventlist
     * It will run until there are no longer events in the list
     */
    public void start() {

        // Stop Criterion
        while ((events.size() > 0) && (!stopFlag)) {
            // Make the similation time equal to the execution time of the first event in the list that has to be processed
            currentTime = events.get(0).getExecutionTime();

            // Let the element be processed
            events.get(0).execute();

            // Remove the event from the list
            events.remove(0);
        }
    }

    /**
     * Method for starting the eventlist
     * It will run until there are no longer events in the list or until the maximum time has been exceeded
     * @param maxTime - The maximum time of the simulation
     */
    public void start(double maxTime) {
        add(this, -1, maxTime);

        // Run the normal start process
        start();
    }

    public void stop() {
        stopFlag = true;
    }

    /**
     * Method for accessing the simulation time
     * The variable with the time is private to ensure that no other object can alter it.
     * This method makes it possible to read the time.
     * @return The current time in the simulation
     */
    public double getCurrentTime() {
        return currentTime;
    }
}
