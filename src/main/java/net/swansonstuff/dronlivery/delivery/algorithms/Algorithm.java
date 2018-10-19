/**
 * 
 */
package net.swansonstuff.dronlivery.delivery.algorithms;

import java.util.Comparator;

import net.swansonstuff.dronlivery.delivery.Delivery;

/**
 * Interface to compare two deliveries for determine the order of delivery.
 * Contract: Should fallback to sorting by order id if both deliveries are equal
 * 
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public abstract class Algorithm implements Comparator<Delivery>{
	abstract public int compare(Delivery one, Delivery two);
}
