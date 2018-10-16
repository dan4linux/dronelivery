/**
 * This class is used to track the delivery metrics
 */
package net.swansonstuff.dronlivery.utils;

import java.util.concurrent.atomic.AtomicInteger;

import net.swansonstuff.dronlivery.delivery.Delivery;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class Metrics {

	public static Metrics instance;
	final AtomicInteger detractors = new AtomicInteger(0);
	final AtomicInteger promoters = new AtomicInteger(0);
	final AtomicInteger total = new AtomicInteger(0);
	

	private Metrics() {		
	}
	
	public static Metrics getInstance() {
		if (instance == null) {
			instance = new Metrics();
		}
		return instance;
	}

	public void track(Delivery delivery) {
		if (delivery.getNps() > 8) {
			promoters.incrementAndGet();
		} else if (delivery.getNps() < 7) {
			detractors.incrementAndGet();
		}
		total.incrementAndGet();
	}


}