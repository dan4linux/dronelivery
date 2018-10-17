/**
 * Object pool used to manage drone armada
 */
package net.swansonstuff.dronlivery.drones;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class DronePool {
	
	private static DronePool instance;
	LinkedBlockingQueue<Drone> droneArmada = new LinkedBlockingQueue<>();
	
	private DronePool() {		
	}

	/**
	 * Return the instance
	 * @return the singleton instance
	 */
	public static DronePool getInstance() {
		if (instance == null) {
			instance = new DronePool();
		}
		return instance;
	}
	
	/**
	 * Remove a drone from the pool (blocking)
	 * @return
	 */
	public Drone getDrone() {
		try {
			return droneArmada.take();
		} catch (InterruptedException e) {
			// don't care
		}
		return null;
	}

	/**
	 * Insert a drone into the pool
	 * @param drone
	 */
	public void add(Drone drone) {
		droneArmada.add(drone);
	}

	/**
	 * Return the current size of the pool of unused drones
	 * @return number of waiting drones
	 */
	public int size() {
		return droneArmada.size();
	}

	/**
	 * For testing -- clear the drone pool
	 */
	public void clearPool() {
		droneArmada.clear();
	}

}
