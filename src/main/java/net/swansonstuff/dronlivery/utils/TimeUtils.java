/**
 * 
 */
package net.swansonstuff.dronlivery.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class TimeUtils {

	private static final Logger log = LoggerFactory.getLogger(TimeUtils.class);
	private static final int PER_MINUTE = 1000 * 60;
	private static final long MILLIS_IN_HOUR = 3600000;
	private static Calendar cal = Calendar.getInstance();

	/**
	 * Returns a date object based on the parsed time "HHmmss" or epoch if unparsable
	 * @param timeString in HHmmss format
	 * @return a date object set to the parsed time
	 */
	public static Date parseTimeString(String timeString) {
		try {
			log.warn("Parsing: {}", timeString);
			MutableDateTime today = new MutableDateTime();
			today.setHourOfDay(Integer.parseInt(timeString.substring(0, 2)));
			today.setMinuteOfHour(Integer.parseInt(timeString.substring(3, 5)));
			today.setSecondOfMinute(Integer.parseInt(timeString.substring(6, 8)));
			return today.toDate();
		} catch (Throwable t) {
			log.error("DOH! ", t);
		}
		return new Date(0);
	}

	/**
	 * Calculates the delivery time from the grid location
	 * @param gridLocation as <direction><distance><direction><distance> (Example: N11W5)
	 * @return the effective distance as an int representing delivery time or -1 on error in millis
	 */
	public static int calcDeliveryTime(String gridLocation) {
		try (Scanner scanner = new Scanner(gridLocation).useDelimiter("[^0-9]")) {
			int distance = 0;
			int gridCoord = scanner.nextInt();
			log.debug("adding {}", gridCoord);
			distance += gridCoord;
			gridCoord = scanner.nextInt();
			log.debug("adding {}", gridCoord);
			distance += gridCoord;
			log.warn("parsed distance: {}", distance);
			distance *= 2; // factor in roundTrip time
			return distance * PER_MINUTE; // round trip cost
		} catch(Throwable t) {
			log.error("DOH!", t);
		}
		return -1;
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
