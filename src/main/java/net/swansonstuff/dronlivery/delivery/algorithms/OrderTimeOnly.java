package net.swansonstuff.dronlivery.delivery.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.swansonstuff.dronlivery.delivery.Delivery;

/**
 * Compares using epoc millis of the order time
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class OrderTimeOnly extends Algorithm {

	private static final OrderTimeOnly instance = new OrderTimeOnly();
	private static final Logger LOG = LoggerFactory.getLogger(OrderTimeOnly.class);

	public static Algorithm getInstance() {
		return instance;
	}

	@Override
	public int compare(Delivery one, Delivery two) {
		long myDeliveryEstimate = one.getOrderTime().getTime();
		long theirDeliveryEstimate = two.getOrderTime().getTime();
		LOG.trace("my estimate: {}, theirEstimate: {}", myDeliveryEstimate, theirDeliveryEstimate);
		if (myDeliveryEstimate == theirDeliveryEstimate) {
			return one.getOrderInfo().compareTo(two.getOrderInfo());
		}

		return (myDeliveryEstimate > theirDeliveryEstimate)?1:-1;
	}

}
