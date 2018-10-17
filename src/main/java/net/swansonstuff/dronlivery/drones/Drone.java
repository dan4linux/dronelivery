/**
 * This class tracks the availability of a single drone
 */
package net.swansonstuff.dronlivery.drones;

import java.io.StringWriter;

import org.joda.time.MutableDateTime;

import net.swansonstuff.dronlivery.delivery.CustomerType;
import net.swansonstuff.dronlivery.delivery.Delivery;
import net.swansonstuff.dronlivery.utils.TimeUtils;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class Drone {
	
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
	
	public Drone deliver(Delivery delivery) {
		long orderTime = delivery.getOrderTime().getTime();
		
		// We cannot leave until the Drone gets back
		timeLeave = Math.max(timeReturn, orderTime);
		delivery.setOrderOutTime(timeLeave);
		timeReturn = timeLeave + delivery.getTransitTime();
		long oneWay = (timeReturn - timeLeave) / 2;
		
		// Time it should have arrived at the customer location
		int deliveryDelta = TimeUtils.getDeliveryDeltaInHours(orderTime, timeLeave + oneWay);
		delivery.setCustomerType(CustomerType.eval(deliveryDelta));
		return this;
	}

	public void free() {
		dronePool.add(this);
	}
	
	@Override
	public String toString() {
		return new StringWriter()
				.append("leave:").append(String.valueOf(timeLeave))
				.append(", return:").append(String.valueOf(timeReturn))
				.toString();
	}
}
