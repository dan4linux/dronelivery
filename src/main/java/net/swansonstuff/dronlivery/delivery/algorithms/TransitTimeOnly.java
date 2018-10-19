package net.swansonstuff.dronlivery.delivery.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.swansonstuff.dronlivery.delivery.Delivery;

/**
 * Compares using the transit time
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class TransitTimeOnly extends Algorithm {

	private static final TransitTimeOnly instance = new TransitTimeOnly();
	private static final Logger LOG = LoggerFactory.getLogger(TransitTimeOnly.class);

	public static Algorithm getInstance() {
		return instance;
	}

	@Override
	public int compare(Delivery one, Delivery two) {
		long myDeliveryEstimate = one.getTransitTime();
		long theirDeliveryEstimate = two.getTransitTime();
		LOG.trace("my estimate: {}, theirEstimate: {}", myDeliveryEstimate, theirDeliveryEstimate);
		if (myDeliveryEstimate == theirDeliveryEstimate) {
			return one.getOrderInfo().compareTo(two.getOrderInfo());
		}

		return (myDeliveryEstimate > theirDeliveryEstimate)?1:-1;
	}

}
