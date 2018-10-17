package net.swansonstuff.dronlivery.drones;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import net.swansonstuff.dronlivery.delivery.CustomerType;
import net.swansonstuff.dronlivery.delivery.Delivery;

public class DroneTest {
	
	@BeforeClass
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
	}
	
	@Test
	public void freeTest() {		
		assertEquals("Drone pool inits empty", 0, DronePool.getInstance().size());
		new Drone().free();
		assertEquals("Free puts drone in pool", 1, DronePool.getInstance().size());
	}
}
