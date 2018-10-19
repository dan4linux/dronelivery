package net.swansonstuff.dronlivery.delivery.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.swansonstuff.dronlivery.delivery.Delivery;

/**
 * Compares using epoc millis of the order time multiplied by the log of the transit time
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class OrderTimeTimesTransitTimeLog extends Algorithm {

	private static final OrderTimeTimesTransitTimeLog instance = new OrderTimeTimesTransitTimeLog();
	private static final Logger LOG = LoggerFactory.getLogger(OrderTimeTimesTransitTimeLog.class);

	public static Algorithm getInstance() {
		return instance;
	}

	@Override
	public int compare(Delivery one, Delivery two) {
		long myDeliveryEstimate = (long)(one.getOrderTime().getTime() * Math.log(one.getTransitTime()));
		long theirDeliveryEstimate = (long)(two.getOrderTime().getTime() * Math.log(two.getTransitTime()));
		LOG.trace("my estimate: {}, theirEstimate: {}", myDeliveryEstimate, theirDeliveryEstimate);
		if (myDeliveryEstimate == theirDeliveryEstimate) {
			return one.getOrderInfo().compareTo(two.getOrderInfo());
		}

		return (myDeliveryEstimate > theirDeliveryEstimate)?1:-1;
	}

}
