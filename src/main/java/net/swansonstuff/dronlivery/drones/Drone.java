/**
 * This class tracks the availability of a single drone
 */
package net.swansonstuff.dronlivery.drones;

import net.swansonstuff.dronlivery.delivery.Delivery;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class Drone {
	
	transient static DronePool dronePool = DronePool.getInstance();
	
	long timeLeave;
	long timeReturn;
	
	public Drone deliver(Delivery delivery) {
		long timeLeave = Math.min(timeReturn, delivery.getOrderTime().getTime());
		timeReturn = timeLeave + delivery.getDeliveryTime();
		return this;
	}

	public void free() {
		dronePool.add(this);
	}
	

}
