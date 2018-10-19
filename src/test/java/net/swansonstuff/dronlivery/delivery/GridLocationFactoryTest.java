package net.swansonstuff.dronlivery.delivery;

import static org.junit.Assert.*;

import org.junit.Test;

public class GridLocationFactoryTest {

	@Test
	public void createTest() {
		assertNotNull("dual single digit parsing", GridLocationFactory.create("N1W1"));
		assertNotNull("single and double digit parsing", GridLocationFactory.create("N1W10"));
		assertNotNull("double and double digit parsing", GridLocationFactory.create("N10W10"));
		assertNull("crap parsing", GridLocationFactory.create("crap"));
	}
}
