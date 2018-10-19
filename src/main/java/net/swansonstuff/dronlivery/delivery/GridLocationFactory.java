package net.swansonstuff.dronlivery.delivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridLocationFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(GridLocationFactory.class);

	public static GridLocation create(String gridLocationString) {
		try {
			return new GridLocation(gridLocationString);
		} catch(Throwable t) {
			LOG.error("Unable to parse locationString '{}' : {} {}", gridLocationString, t.getClass().getSimpleName(), t.getMessage());
		}
		return null;
	}

}
