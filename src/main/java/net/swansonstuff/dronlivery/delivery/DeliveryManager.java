/**
 * This class manages the delivery manifest and ordering
 * 
 */
package net.swansonstuff.dronlivery.delivery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class DeliveryManager {

	private static final Logger log = LoggerFactory.getLogger(DeliveryManager.class);
	private static final String DELIMITER = System.getProperty("delivery.field.delimiter", " ");
	private static DeliveryManager instance;
	private TreeSet<Delivery> deliveries = new TreeSet<>();

	private DeliveryManager() {
	}

	public static DeliveryManager getInstance() {
		if (instance == null) {
			instance = new DeliveryManager();
		}
		return instance;
	}

	public void addDelivery(String orderInfo, String gridLocation, String timeString) {
		deliveries.add(new Delivery(orderInfo, gridLocation, timeString));
	}

	public TreeSet<Delivery> getDeliveries() {
		return deliveries;
	}

	public void loadDeliveries(File scheduleFile) {
		try (BufferedReader br = new BufferedReader(new FileReader(scheduleFile))){

			String buf = null;
			String[] parts;

			while ((buf = br.readLine())!= null) {
				parts = buf.split(DELIMITER);
				if (parts.length != 3) {
					log.warn("Invalid data: {}", buf);
					continue;
				}
				addDelivery(parts[0], parts[1], parts[2]);
			}

		} catch (Throwable t) {
			if (log.isDebugEnabled()) {
				log.error("DOH!", t);
			} else {
				log.error("DOH! {}:{}", t.getClass().getSimpleName(), t.getMessage());
			}
		}
	}

}
