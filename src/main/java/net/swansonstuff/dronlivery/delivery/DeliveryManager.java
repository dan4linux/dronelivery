/**
 * This class manages the delivery manifest and ordering
 * 
 */
package net.swansonstuff.dronlivery.delivery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.SortedSet;
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
	SortedSet<Delivery> deliveries = Collections.synchronizedSortedSet(new TreeSet<>(Delivery::compareTo));

	private DeliveryManager() {
	}

	public static DeliveryManager getInstance() {
		if (instance == null) {
			instance = new DeliveryManager();
		}
		return instance;
	}

	public Delivery addDelivery(String orderInfo, String gridLocation, String timeString) {
		Delivery delivery = new Delivery(orderInfo, gridLocation, timeString);
		if (!deliveries.add(delivery)) {
			log.warn("Unable to add delivery: {}({})", delivery, delivery.hashCode());
		}
		log.debug("Delivery count: {}", deliveries.size());
		return delivery;
	}

	public SortedSet<Delivery> getDeliveries() {
		return deliveries;
	}

	public void loadDeliveries(File scheduleFile) {
		try (BufferedReader br = new BufferedReader(new FileReader(scheduleFile))){

			String buf = null;
			String[] parts;

			while ((buf = br.readLine())!= null) {
				try {
					parts = buf.split(DELIMITER);
					if (parts.length != 3) {
						log.warn("Invalid data: {}", buf);
						continue;
					}
					Delivery delivery = addDelivery(parts[0], parts[1], parts[2]);
					log.debug("added: {}({})", buf, delivery.hashCode());
				} catch(Throwable t) {
					log.error("Can't add line: {}->{} => {}", t.getClass().getSimpleName(), t.getMessage(), buf);
				}
			}

		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void reset() {
		deliveries.clear();
	}

}
