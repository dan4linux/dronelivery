/**
 * This class tracks the availability of a single drone
 */
package net.swansonstuff.dronlivery.drones;

import java.io.StringWriter;

import org.joda.time.MutableDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.swansonstuff.dronlivery.delivery.CustomerType;
import net.swansonstuff.dronlivery.delivery.Delivery;
import net.swansonstuff.dronlivery.utils.TimeUtils;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class Drone {
	
	transient static final Logger LOG = LoggerFactory.getLogger(Drone.class);
	transient static DronePool dronePool = DronePool.getInstance();
	transient static int startHour = Integer.parseInt(System.getProperty("start.hour", "6")); 
	
	long timeLeave;
	long timeReturn;
	
	public Drone() {
		MutableDateTime mdt = new MutableDateTime();
		mdt.setMillisOfSecond(0);
		mdt.setSecondOfDay(0);
		mdt.setMinuteOfHour(0);
		mdt.setHourOfDay(startHour);
		timeReturn = mdt.toDate().getTime();
	}
	
	/**
	 * Perform a simulated delivery and track the metrics from the trip
	 * @param delivery the package to be delivered
	 * @return the Drone object
	 */
	public Drone deliver(Delivery delivery) {
		try {
			long orderTime = delivery.getOrderTime().getTime();

			// We cannot leave until the Drone gets back
			timeLeave = Math.max(timeReturn, orderTime);
			delivery.setOrderOutTime(timeLeave);
			timeReturn = timeLeave + delivery.getTransitTime();
			long oneWay = (timeReturn - timeLeave) / 2;

			// Time it should have arrived at the customer location
			int deliveryDelta = TimeUtils.getDeliveryDeltaInHours(orderTime, timeLeave + oneWay);
			delivery.setCustomerType(CustomerType.eval(deliveryDelta));
		} catch(Throwable t)  {
			LOG.error("Failed to process delivery {}: {} {}", delivery.getOrderInfo(), t.getClass().getSimpleName(), t.getMessage());
		}
		return this;
	}

	/**
	 * Free the drone to return to the pool for future use
	 */
	public void free() {
		dronePool.add(this);
	}
	
	/**
	 * For logging purposes
	 */
	@Override
	public String toString() {
		return new StringWriter()
				.append("leave:").append(String.valueOf(timeLeave))
				.append(", return:").append(String.valueOf(timeReturn))
				.toString();
	}
}
