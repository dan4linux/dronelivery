/**
 * This class tracks the availability of a single drone
 */
package net.swansonstuff.dronlivery.drones;

import net.swansonstuff.dronlivery.delivery.CustomerType;
import net.swansonstuff.dronlivery.delivery.Delivery;
import net.swansonstuff.dronlivery.utils.TimeUtils;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class Drone {
	
	transient static DronePool dronePool = DronePool.getInstance();
	
	long timeLeave;
	long timeReturn;
	
	public Drone deliver(Delivery delivery) {
		long orderTime = delivery.getOrderTime().getTime();
		long timeLeave = Math.min(timeReturn, orderTime);
		timeReturn = timeLeave + delivery.getDeliveryTime();
		long oneWay = (timeReturn - timeLeave) / 2;
		int deliveryDelta = TimeUtils.getDeliveryDeltaInHours(orderTime, timeLeave + oneWay);
		delivery.setCustomerType(CustomerType.eval(deliveryDelta));
		return this;
	}

	public void free() {
		dronePool.add(this);
	}
	

}
