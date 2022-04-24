package nl.maastrichtuniversity.supermarket.contracts;

import nl.maastrichtuniversity.supermarket.models.Product;

/**
 * Blueprint for accepting products
 * Classes that implement this interface can accept products
 * @author Joel Karel
 */
public interface ProductAcceptor {

    /**
     * Method to have this object process an event
     * @param product The product that is accepted
     * @return true if accepted
     */
    boolean giveProduct(Product product);
}
