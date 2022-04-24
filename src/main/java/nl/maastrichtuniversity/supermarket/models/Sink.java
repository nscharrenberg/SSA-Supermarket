package nl.maastrichtuniversity.supermarket.models;

import nl.maastrichtuniversity.supermarket.contracts.ProductAcceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * A Sink
 * @author Joel Karel
 * @author Noah Scharrenberg
 */
public class Sink implements ProductAcceptor {
    /**
     * All Products are kept
     */
    private List<Product> products;

    /**
     * All properties of products are kept
     */
    private List<Integer> numbers;


    private List<Double> times;
    private List<String> events;
    private List<String> stations;

    /**
     * Counter to number products
     */
    private int number;

    /**
     * Name of the sink
     */
    private final String name;

    public Sink(String name) {
        this.name = name;
        this.products = new ArrayList<>();
        this.numbers = new ArrayList<>();
        this.times = new ArrayList<>();
        this.events = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.number = 0;
    }

    @Override
    public boolean giveProduct(Product product) {
        number++;

        products.add(product);

        List<Double> t = product.getTimes();
        List<String> e = product.getEvents();
        List<String> s = product.getStations();

        for (int i = 0; i < t.size(); i++) {
            numbers.add(number);
            times.add(t.get(i));
            events.add(e.get(i));
            stations.add(s.get(i));
        }

        return true;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Integer> getNumbers() {
        return numbers;
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

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
