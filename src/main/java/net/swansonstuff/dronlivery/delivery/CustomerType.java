/**
 * 
 */
package net.swansonstuff.dronlivery.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public enum CustomerType {
	UNKNOWN(-1,-1), PROMOTER(0,2), NEUTRAL(2,4), DETRACTOR(4, Integer.MAX_VALUE);

	private static final Logger log = LoggerFactory.getLogger(CustomerType.class);
	
	private int low;
	private int high;
	private CustomerType(int lowScore, int highScore) {
		this.low = lowScore;
		this.high = highScore;
	}
	
	public static CustomerType eval(int score) {
		for (CustomerType val : values()) {
			if (score >= val.low && score < val.high) {
				return val;
			}
		}
		log.error("DOH!", new Exception("Sending unknown for "+ score));
		return UNKNOWN;
	}
}
