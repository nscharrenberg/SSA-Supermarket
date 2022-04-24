package nl.maastrichtuniversity.supermarket.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Product that is send through the system
 * @author Joel Karel
 * @author Noah Scharrenberg
 */
public class Product {
    private final List<Double> times;
    private final List<String> events;
    private final List<String> stations;

    /**
     * Constructor for the product
     * Mark the time at which it's created
     * @param create the current time
     */
    public Product() {
        this.times = new ArrayList<>();
        this.events = new ArrayList<>();
        this.stations = new ArrayList<>();
    }

    public void stamp(double time, String event, String station) {
        times.add(time);
        events.add(event);
        stations.add(station);
    }

    public List<Double> getTimes() {
        return times;
    }

    public List<String> getEvents() {
        return events;
    }

    public List<String> getStations() {
        return stations;
    }
}
