/**
 * 
 */
package net.swansonstuff.dronlivery.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class TimeUtils {

	private static final Logger log = LoggerFactory.getLogger(TimeUtils.class);
	static final SimpleDateFormat dateParser = new SimpleDateFormat("HH:mm:ss");
	private static final int PER_MINUTE = 1000 * 60;

	/**
	 * Returns a date object based on the parsed time "HH:mm:ss" or epoch if unparsable
	 * @param timeString in HH:mm:ss format
	 * @return a date object set to the parsed time
	 */
	public static Date parse(String timeString) {
		try {
			return dateParser.parse(timeString);
		} catch (ParseException pe) {
			log.error("DOH! ", pe);
		}
		return new Date(0);
	}

	/**
	 * Calculates the delivery time from the grid location
	 * @param gridLocation as <direction><distance><direction><distance> (Example: N11W5)
	 * @return the effective distance as an int representing delivery time or -1 on error in millis
	 */
	public static int parseDeliveryTime(String gridLocation) {
		try (Scanner scanner = new Scanner(gridLocation)) {
			int distance = 0;
			log.debug("discarding {}", scanner.next("[^0-9]+"));
			int gridCoord = scanner.nextInt();
			log.debug("adding {}", gridCoord);
			distance += gridCoord;
			log.debug("discarding {}", scanner.next("[^0-9]+"));
			gridCoord = scanner.nextInt();
			log.debug("adding {}", gridCoord);
			distance += gridCoord;
			
			distance *= 2; // factor in roundTrip time
			return distance * PER_MINUTE; // round trip cost
		} catch(Throwable t) {
			log.error("DOH!", t);
		}
		return -1;
	}

}
