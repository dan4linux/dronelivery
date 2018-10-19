/**
 * 
 */
package net.swansonstuff.dronlivery.utils;

import java.util.Date;

import org.joda.time.MutableDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.swansonstuff.dronlivery.delivery.GridLocation;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class TimeUtils {

	private static final Logger log = LoggerFactory.getLogger(TimeUtils.class);
	private static final int PER_MINUTE = 1000 * 60;
	private static final long MILLIS_IN_HOUR = 3600000;
	private static final int HANDLING_MILLIS = Integer.parseInt(System.getProperty("handling.time.millis", "1000"));

	/**
	 * Returns a date object based on the parsed time "HHmmss" or epoch if unparsable
	 * @param timeString in HHmmss format
	 * @return a date object set to the parsed time
	 */
	public static Date parseTimeString(String timeString) {
		log.trace("Parsing: {}", timeString);
		MutableDateTime today = new MutableDateTime();
		today.setHourOfDay(Integer.parseInt(timeString.substring(0, 2)));
		today.setMinuteOfHour(Integer.parseInt(timeString.substring(3, 5)));
		today.setSecondOfMinute(Integer.parseInt(timeString.substring(6, 8)));
		return today.toDate();
	}

	/**
	 * Calculates the delivery time from the grid location
	 * @param gridLocation GridLocation object
	 * @return the effective distance as an int representing delivery time or -1 on error in millis
	 */
	public static int calcDeliveryTime(GridLocation gridLocation) {
		
		if (gridLocation == null) {
			return -1;
		}

		double distance = Math.pow(gridLocation.getVerticalDistance() * 10, 2);			
		distance += Math.pow(gridLocation.getHorizontalDistance() * 10, 2);
		// Pythagorize
		distance = Math.sqrt(distance);

		distance *= 2; // factor in roundTrip time
		
		int timeInMillis = (int)Math.ceil(distance / 10 * PER_MINUTE) ;
		return timeInMillis + HANDLING_MILLIS;
	}

	/**
	 * Calculate the time in hours from when the package was ordered to when it arrived
	 * @param orderTime time package was ordered
	 * @param packageArrived time package was delivered to the customer
	 * @return delta in hours
	 */
	public static int getDeliveryDeltaInHours(long orderTime, long packageArrived) {
		long delta = (packageArrived - orderTime) / MILLIS_IN_HOUR;
		return delta > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)delta;
	}

}
