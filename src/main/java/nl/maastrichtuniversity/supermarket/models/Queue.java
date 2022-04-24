package nl.maastrichtuniversity.supermarket.models;

import nl.maastrichtuniversity.supermarket.contracts.ProductAcceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Queue that stores products until they can be handled by a machine
 * @author Joel Karel
 * @author Noah Scharrenberg
 */
public class Queue implements ProductAcceptor {
    /**
     * List in which the products are kept
     */
    private List<Product> row;

    /**
     * Requests from the machine that will be handling the products
     */
    private List<Machine> requests;

    public Queue() {
        this.row = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    /**
     * Asks a queue to give a product to a machine
     * @param machine The machine?? TODO: Figure out what the machine parameter is supposed to used for
     * @return True is returned if a product could be delivered; false if the request is queued
     */
    public boolean askProduct(Machine machine) {
        if (row.size() > 0) {
            if (machine.giveProduct(row.get(0))) {
                row.remove(0);
                return true;
            }

            return false;
        }

        requests.add(machine);
        return false;
    }

    /**
     * Offer a product to the queue
     * It's investigated whethr a machine wants the product, otherwise it's stored
     * @param p The product that is accepted
     * @return always return true. TODO: Maybe figure out why we return true at all times?
     */
    @Override
    public boolean giveProduct(Product p) {
        // Check if the machine accepts the product
        if (requests.size() < 1) {
            // Store the product if it does not
            row.add(p);

            return true;
        }

        boolean delivered = false;

        while (!delivered & (requests.size() > 0)) {
            delivered = requests.get(0).giveProduct(p);

            // remove the request regardless of whether the product has been accepted
            requests.remove(0);
        }

        if (!delivered) {
            // If it's not delivered then Store it
            row.add(p);
        }

        return true;
    }
}
