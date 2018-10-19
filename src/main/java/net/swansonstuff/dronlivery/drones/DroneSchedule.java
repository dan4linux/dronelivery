/**
 * 
 */
package net.swansonstuff.dronlivery.drones;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class DroneSchedule {

	private static DroneSchedule instance;
	private DronePool dronePool = DronePool.getInstance();
	
	private DroneSchedule(int droneCount) {
		while (droneCount-- > 0) {
			dronePool.add(new Drone());
		}
	}

	public static DroneSchedule getInstance() {
		if (instance == null) {
			instance = new DroneSchedule(Integer.parseInt(System.getProperty("drones", "1")));
		}
		return instance;
	}

	public Drone getNextDrone() {
		return dronePool.getDrone();
	}
	
	public void reset() {
		dronePool.resetDrones();
	}

}
