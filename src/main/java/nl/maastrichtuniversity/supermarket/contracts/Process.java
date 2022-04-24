package nl.maastrichtuniversity.supermarket.contracts;

/**
 * Blueprint for processes
 * Classes that implement this interface can process events
 * @author Joel Karel
 * @author Noah Scharrenberg
 */
public interface Process {
    /**
     * Method to have this object process an event
     * @param type The type of the event that has to be executed
     * @param time The current time
     */
    public void execute(int type, double time);
}
