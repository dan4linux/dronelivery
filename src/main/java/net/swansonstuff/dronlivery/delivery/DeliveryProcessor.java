/**
 * This class reads in the file and renders the objects into a TreeMap
 */
package net.swansonstuff.dronlivery.delivery;

import java.io.File;
import java.io.PrintStream;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.swansonstuff.dronlivery.delivery.algorithms.Algorithm;
import net.swansonstuff.dronlivery.delivery.algorithms.AlgorithmManager;
import net.swansonstuff.dronlivery.drones.DroneSchedule;
import net.swansonstuff.dronlivery.utils.Metrics;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class DeliveryProcessor {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeliveryProcessor.class);
	
	private File scheduleFile;
	private Metrics metrics = Metrics.getInstance();
	private DroneSchedule droneScheduler = DroneSchedule.getInstance();
	private DeliveryManager delMan = DeliveryManager.getInstance();

	private PrintStream printStream;

	public DeliveryProcessor(String inputFilePath, PrintStream printStream) {
		this.scheduleFile = new File(inputFilePath);
		this.printStream = printStream;
	}

	/**
	 * Entry point for the class
	 */
	public void run(AlgorithmManager algorithmManager) {
		if (!scheduleFile.exists()) {
			throw new RuntimeException("File does not exist or is not accessible: "+scheduleFile.getAbsolutePath());
		}
		
		delMan.loadDeliveries(scheduleFile);
		Algorithm bestAlgorithm = findBestAlgorithm(algorithmManager);
		System.out.println("Use delivery file: "+generateAlgorithmOutputFileName(bestAlgorithm));

	}

	/**
	 * @return Algorithm with the best score
	 */
	public Algorithm findBestAlgorithm(AlgorithmManager algorithmManager) {
		TreeMap<Integer, Algorithm> algorithmResults = new TreeMap<>();
		PrintStream saved = printStream;
		
		algorithmManager.getAlgorithms().stream().forEach(algorithm->{
			String algName = algorithm.getClass().getSimpleName();
			try (PrintStream algPrintStream = new PrintStream(generateAlgorithmOutputFileName(algorithm))){
				if (saved == null) {
					printStream = algPrintStream;
				}
				delMan.getDeliveries().stream().sorted(algorithm).forEach(this::doDelivery);
				int key = Integer.parseInt(metrics.toString().substring(4));
				LOG.debug("test results: {} {}", algName, key);
				algorithmResults.put(key, algorithm);
				printStream.print(metrics);
			} catch(Throwable t) {
				LOG.error("Cannot complete algorithm test for {}: {} {}", algName);
			} finally {
				metrics.reset();
				droneScheduler.reset();
			}
		});
		
		printStream = saved;
		
		// TreeMap ... and we want the highest rated algorithm
		Algorithm bestAlgorithm = algorithmResults.lastEntry().getValue();
		return bestAlgorithm;
	}
	
	/**
	 * Testable handler for streams (Does not output the results)
	 * @param delivery
	 */
	void testDelivery(Delivery delivery) {
		doDelivery(delivery, false);
		System.err.println(delivery);
	}
	
	/**
	 * Final processing handler for streams (output the results)
	 * @param delivery
	 * @param finalRun
	 */
	void doDelivery(Delivery delivery) {
		doDelivery(delivery, true);
	}
	
	
	void doDelivery(Delivery delivery, boolean finalRun) {
		droneScheduler.getNextDrone().deliver(delivery).free();
		metrics.track(delivery);
		if (finalRun) {
			printStream.println(delivery);
		}
	}

	private String generateAlgorithmOutputFileName(Algorithm bestAlgorithm) {
		return scheduleFile+"."+bestAlgorithm.getClass().getSimpleName();
	}

}
