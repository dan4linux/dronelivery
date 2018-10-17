/**
 * 
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

	public static DronePool getInstance() {
		if (instance == null) {
			instance = new DronePool();
		}
		return instance;
	}
	
	public Drone getDrone() {
		try {
			return droneArmada.take();
		} catch (InterruptedException e) {
			// don't care
		}
		return null;
	}

	public void add(Drone drone) {
		droneArmada.add(drone);
	}

	public int size() {
		return droneArmada.size();
	}

	public void clearPool() {
		droneArmada.clear();
	}

}
