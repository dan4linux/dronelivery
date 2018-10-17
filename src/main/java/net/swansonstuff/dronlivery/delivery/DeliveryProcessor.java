/**
 * This class reads in the file and renders the objects into a TreeMap
 */
package net.swansonstuff.dronlivery.delivery;

import java.io.File;
import java.io.PrintStream;

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
	private PrintStream printStream;

	public DeliveryProcessor(String inputFilePath, PrintStream printStream) {
		this.scheduleFile = new File(inputFilePath);
		this.printStream = printStream;
	}

	public void run() {
		if (!scheduleFile.exists()) {
			throw new RuntimeException("File does not exist or is not accessible: "+scheduleFile.getAbsolutePath());
		}
		
		DeliveryManager delMan = DeliveryManager.getInstance();
		delMan.loadDeliveries(scheduleFile);
		delMan.getDeliveries().stream().forEach(this::doDelivery);

		printStream.print(metrics);
	}
	
	public void doDelivery(Delivery delivery) {
		droneScheduler.getNextDrone().deliver(delivery).free();
		printStream.println(delivery);
		metrics.track(delivery);
	}

}
