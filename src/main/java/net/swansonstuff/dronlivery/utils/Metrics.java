/**
 * This class is used to track the delivery metrics
 */
package net.swansonstuff.dronlivery.utils;

import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.swansonstuff.dronlivery.delivery.Delivery;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class Metrics {

	private static final Logger LOG = LoggerFactory.getLogger(Metrics.class); 
	
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
		switch(delivery.getCustomerType()) {
			case PROMOTER:
				promoters.incrementAndGet();
				break;
			case DETRACTOR:
				detractors.incrementAndGet();
				break;
			default:
				// don't care
		}
		total.incrementAndGet();
		LOG.info("total: {}, prom: {}, det: {}", total.get(), promoters.get(), detractors.get());
	}

	@Override
	public String toString() {
		int promPercent = promoters.get() * 100 / total.get();
		int detPercent = detractors.get() * 100 / total.get();
		return new StringWriter().append(String.valueOf(promPercent - detPercent)).toString();
	}

	public void reset() {
		detractors.set(0);
		promoters.set(0);
		total.set(0);
	}

}
