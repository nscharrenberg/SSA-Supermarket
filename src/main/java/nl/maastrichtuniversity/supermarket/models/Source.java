package nl.maastrichtuniversity.supermarket.models;

import nl.maastrichtuniversity.supermarket.contracts.Process;
import nl.maastrichtuniversity.supermarket.contracts.ProductAcceptor;
import nl.maastrichtuniversity.supermarket.utils.Util;

/**
 * A source of products
 * This class implements Process so that it can execute events
 * By continuously creating new events, the source keeps busy
 * @author Joel Karel
 * @author Noah Scharrenberg
 */
public class Source implements Process {
    /**
     * EventList that will be requested to construct events
     */
    private EventList list;

    /**
     * Queue that buffers products for the machine
     */
    private ProductAcceptor queue;

    /**
     * Name of the source
     */
    private String name;

    /**
     * Mean Inter arrival time
     */
    private double meanInterArrivalTime;

    /**
     * Inter arrival times (in case pre-specified)
     */
    private double[] interArrivalTimes;

    /**
     * Inter arrival time iterator
     */
    private int interArrCnt;

    /**
     * Constructor, Creates objects inter arrival times are exponentially distributed with mean 33
     * @param queue The received of the products
     * @param list The eventlist that is requested to construct events
     * @param name Name of the object
     */
    public Source(ProductAcceptor queue, EventList list, String name) {
        this.list = list;
        this.queue = queue;
        this.name = name;
        this.meanInterArrivalTime = 33;
        this.list.add(this, 0, Util.drawRandomExponential(meanInterArrivalTime));
    }

    /**
     * Constructor, Creates objects inter arrival times are exponentially distributed with specified mean
     * @param queue The received of the products
     * @param list The eventlist that is requested to construct events
     * @param name Name of the object
     * @param meanInterArrivalTime Inter arrival time
     */
    public Source(ProductAcceptor queue, EventList list, String name, double meanInterArrivalTime) {
        this.list = list;
        this.queue = queue;
        this.name = name;
        this.meanInterArrivalTime = meanInterArrivalTime;
        this.list.add(this, 0, Util.drawRandomExponential(meanInterArrivalTime));
    }

    /**
     * Constructor, Creates objects inter arrival times are exponentially distributed with specified mean
     * @param queue The received of the products
     * @param list The eventlist that is requested to construct events
     * @param name Name of the object
     * @param interArrivalTimes Inter arrival times
     */
    public Source(ProductAcceptor queue, EventList list, String name, double[] interArrivalTimes) {
        this.list = list;
        this.queue = queue;
        this.name = name;
        this.meanInterArrivalTime = -1;
        this.interArrivalTimes = interArrivalTimes;
        this.interArrCnt = 0;
        this.list.add(this, 0, interArrivalTimes[0]);
    }

    @Override
    public void execute(int type, double time) {
        System.out.println("Arrival at time = " + time);

        Product product = new Product();
        product.stamp(time, "Creation", name);
        queue.giveProduct(product);

        if (meanInterArrivalTime > 0) {
            double duration = Util.drawRandomExponential(meanInterArrivalTime);
            list.add(this, 0, time + duration);

            return;
        }

        interArrCnt++;

        if (interArrivalTimes.length > interArrCnt) {
            list.add(this, 0, time + interArrivalTimes[interArrCnt]);
            return;
        }

        list.stop();
    }
}
