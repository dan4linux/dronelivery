package net.swansonstuff.dronlivery.drones;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.swansonstuff.dronlivery.delivery.CustomerType;
import net.swansonstuff.dronlivery.delivery.Delivery;

public class DroneTest {
	
	@Before
	public void setup() {
		DronePool.getInstance().clearPool();
	}

	@Test
	public void deliverTest() {
		Drone drone = new Drone();
		Delivery delivery = new Delivery("WM001", "N11W5", "05:11:50");
		drone.deliver(delivery);
		assertEquals("CustomerType is ", CustomerType.PROMOTER, delivery.getCustomerType());
		drone.timeReturn += 7200000;
		drone.deliver(delivery);
		assertEquals("CustomerType is ", CustomerType.NEUTRAL, delivery.getCustomerType());
		drone.timeReturn += 7200000;
		drone.deliver(delivery);
		assertEquals("CustomerType is ", CustomerType.DETRACTOR, delivery.getCustomerType());
		boolean caughtException = false;
		try {
			drone.deliver(new Delivery(null,null, null));
		} catch(Exception e) {
			caughtException = true;
		}
		assertTrue("Exception on null object", caughtException);
		
		assertTrue("description contains required values", drone.toString().contains("leave") && drone.toString().contains("return"));
	}
	
	@Test
	public void freeTest() {		
		assertEquals("Drone pool inits empty", 0, DronePool.getInstance().size());
		new Drone().free();
		assertEquals("Free puts drone in pool", 1, DronePool.getInstance().size());
	}
}
