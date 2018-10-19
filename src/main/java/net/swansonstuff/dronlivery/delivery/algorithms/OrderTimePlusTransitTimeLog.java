package net.swansonstuff.dronlivery.delivery.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.swansonstuff.dronlivery.delivery.Delivery;

/**
 * Compares using epoc millis of the order time + log of the transit time
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class OrderTimePlusTransitTimeLog extends Algorithm {

	private static final OrderTimePlusTransitTimeLog instance = new OrderTimePlusTransitTimeLog();
	private static final Logger LOG = LoggerFactory.getLogger(OrderTimePlusTransitTimeLog.class);

	public static Algorithm getInstance() {
		return instance;
	}

	@Override
	public int compare(Delivery one, Delivery two) {
		long myDeliveryEstimate = (long)((one.getOrderTime().getTime()/ 60000) + Math.log(one.getTransitTime()));
		long theirDeliveryEstimate = (long)((two.getOrderTime().getTime()/60000) + Math.log(two.getTransitTime()));
		LOG.trace("my estimate: {}({}), theirEstimate: {}({})", myDeliveryEstimate, Math.log(one.getTransitTime()), theirDeliveryEstimate, Math.log(two.getTransitTime()));
		if (myDeliveryEstimate == theirDeliveryEstimate) {
			return one.getOrderInfo().compareTo(two.getOrderInfo());
		}

		return (myDeliveryEstimate > theirDeliveryEstimate)?1:-1;
	}

}
