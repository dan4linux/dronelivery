/**
 * This class reads in the file and renders the objects into a TreeMap
 */
package net.swansonstuff.dronlivery.delivery;

import java.io.File;

import net.swansonstuff.dronlivery.drones.DroneSchedule;
import net.swansonstuff.dronlivery.utils.Metrics;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class DeliveryProcessor {
	
	private File scheduleFile;
	private Metrics metrics = Metrics.getInstance();
	private DroneSchedule droneScheduler = DroneSchedule.getInstance();

	public DeliveryProcessor(String filePath) {
		this.scheduleFile = new File(filePath);
	}

	public void run() {
		if (!scheduleFile.exists()) {
			throw new RuntimeException("File does not exist or is not accessible: "+scheduleFile.getAbsolutePath());
		}
		
		DeliveryManager delMan = DeliveryManager.getInstance();
		delMan.loadDeliveries(scheduleFile);
		delMan.getDeliveries().stream().forEach(this::doDelivery);

	}
	
	public void doDelivery(Delivery delivery) {
		droneScheduler.getNextDrone().deliver(delivery).free();
		System.out.println(delivery);
		metrics.track(delivery);
	}

}
