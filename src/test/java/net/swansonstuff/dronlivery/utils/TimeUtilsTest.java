package net.swansonstuff.dronlivery.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.MutableDateTime;
import org.junit.Test;

public class TimeUtilsTest {
	
	@Test
	public void parseTimeStringTest() {
		Date reference = new Date();
		String dateString = new SimpleDateFormat("HH:mm:ss").format(reference);
		Date test = TimeUtils.parseTimeString(dateString);
		assertEquals("Hard date matches parsed date", reference.getTime()/1000, test.getTime()/1000);
		test = TimeUtils.parseTimeString("");
		assertEquals("Bad date returns epoch date", 0L, test.getTime()/1000);
	}
	
	@Test
	public void calcDeliveryTimeTest() {
		int distance = TimeUtils.calcDeliveryTime("N1W1");
		assertEquals("dual single digit parsing", 120000*2, distance);
		distance = TimeUtils.calcDeliveryTime("N1W10");
		assertEquals("single and double digit parsing", 660000*2, distance);
		distance = TimeUtils.calcDeliveryTime("N10W10");
		assertEquals("double and double digit parsing", 1200000*2, distance);
		distance = TimeUtils.calcDeliveryTime("crap");
		assertEquals("crap parsing", -1, distance);
	}
	
	@Test
	public void getDeliveryDeltaInHoursTest() {
		int delta = TimeUtils.getDeliveryDeltaInHours(1, 2);
		assertEquals("0 hours", 0, delta);
		delta = TimeUtils.getDeliveryDeltaInHours(1, 3600002);
		assertEquals("1 hours", 1, delta);
		long timeStamp = System.currentTimeMillis();
		MutableDateTime now = new MutableDateTime(timeStamp);
		long reference = now.toDate().getTime();
		now.addMillis(3600002);
		delta = TimeUtils.getDeliveryDeltaInHours(reference, now.toDate().getTime());
		assertEquals("1 hours from now", 1, delta);
		now.addYears(5);
		delta = TimeUtils.getDeliveryDeltaInHours(reference, Long.MAX_VALUE);
		assertEquals("insaneTime", Integer.MAX_VALUE, delta);
	}

}
