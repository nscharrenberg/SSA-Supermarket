package nl.maastrichtuniversity.supermarket.models;

import nl.maastrichtuniversity.supermarket.contracts.Process;
import nl.maastrichtuniversity.supermarket.contracts.ProductAcceptor;
import nl.maastrichtuniversity.supermarket.utils.Util;

/**
 * Machine in a factory
 * @author Joel Karel
 * @author Noah Scharrenberg
 */
public class Machine implements Process, ProductAcceptor {
    /**
     * Product that is being handled
     */
    private Product product;

    /**
     * EventList that will manage events
     */
    private final EventList eventList;

    /**
     * Queue from which the machine has to take products
     */
    private Queue queue;

    /**
     * Sink to dump products
     */
    private ProductAcceptor sink;

    /**
     * Status of the machine (b = busy, i = idle)
     */
    private char status;

    /**
     * Name of the Machine
     */
    private final String name;

    /**
     * Mean Processing TIme
     */
    private double meanProcTime;

    /**
     * Processing Times (in case pre-specified
     */
    private double[] processingTimes;

    /**
     * Processing time Iterator
     */
    private int procCnt;

    /**
     * Service times are exponentially distributed with mean 30
     * @param queue Queue from which the machine has to take products
     * @param sink Where to send the completed products
     * @param eventList EventList that will manage events
     * @param name The name of the machine
     */
    public Machine(Queue queue, ProductAcceptor sink, EventList eventList, String name) {
        this.status = 'i';
        this.eventList = eventList;
        this.queue = queue;
        this.sink = sink;
        this.name = name;
        this.meanProcTime = 30;
        this.queue.askProduct(this);
    }

    /**
     * Service times are exponentially distributed with specified mean
     * @param queue Queue from which the machine has to take products
     * @param sink Where to send the completed products
     * @param eventList EventList that will manage events
     * @param name The name of the machine
     * @param meanProcTime The mean processing time
     */
    public Machine(Queue queue, ProductAcceptor sink, EventList eventList, String name, double meanProcTime) {
        this.status = 'i';
        this.eventList = eventList;
        this.queue = queue;
        this.sink = sink;
        this.name = name;
        this.meanProcTime = meanProcTime;
        this.queue.askProduct(this);
    }

    /**
     * Service times are exponentially distributed with specified mean
     * @param queue Queue from which the machine has to take products
     * @param sink Where to send the completed products
     * @param eventList EventList that will manage events
     * @param name The name of the machine
     * @param processingTimes The service times
     */
    public Machine(Queue queue, ProductAcceptor sink, EventList eventList, String name, double[] processingTimes) {
        this.status = 'i';
        this.eventList = eventList;
        this.queue = queue;
        this.sink = sink;
        this.name = name;
        this.meanProcTime = -1;
        this.processingTimes = processingTimes;
        this.procCnt = 0;
        this.queue.askProduct(this);
    }

    /**
     * Method to have this object execute an event
     * @param type The type of the event that has to be executed
     * @param time The current time
     */
    @Override
    public void execute(int type, double time) {
        // Show Arrival
        System.out.println("Product finished at time = " + time);

        // Remove product from system
        product.stamp(time, "Production Complete", name);
        sink.giveProduct(product);

        product = null;

        // Machine goes back to idle
        status = 'i';

        // Ask the queue for products
        queue.askProduct(this);
    }

    /**
     * Let the machine accept a product and let it start handling it
     * @param product The product that is offered
     * @return true if the product is accepted and started, false otherwise
     */
    @Override
    public boolean giveProduct(Product product) {
        // Only accept something if machine is idle
        if (status == 'i') {

            // Accept the product
            this.product = product;

            // Mark the starting time
            this.product.stamp(eventList.getCurrentTime(), "Production Started", name);

            // Start Production
            startProduction();

            // Product has arrived and accepted
            return true;
        }

        // Product has been rejected
        return false;
    }

    /**
     * Starting routine for the production
     * Start the handling of the current product with an exponentially distributed processing time with average 30
     * This time is placed in the event list
     */
    private void startProduction() {
        if (meanProcTime > 0) {
            // Generate Duration
            double duration = Util.drawRandomExponential(meanProcTime);

            // Create a new event in the eventlist
            double time = eventList.getCurrentTime();
            eventList.add(this, 0, time + duration);

            status = 'b';

            return;
        }

        if (processingTimes.length > procCnt) {
            eventList.add(this, 0, eventList.getCurrentTime() + processingTimes[procCnt]);
            status = 'b';
            procCnt++;

            return;
        }

        eventList.stop();
    }
}
