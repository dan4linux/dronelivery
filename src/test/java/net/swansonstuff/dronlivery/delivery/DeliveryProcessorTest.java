package net.swansonstuff.dronlivery.delivery;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import net.swansonstuff.dronlivery.drones.DronePool;
import net.swansonstuff.dronlivery.utils.Metrics;

public class DeliveryProcessorTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setup() {
		Metrics.getInstance().reset();
		DronePool.getInstance().clearPool();
	}


	@Test
	public void runTest() throws IOException {
		PrintStream printStream = new PrintStream(outContent);
		new DeliveryProcessor("target/test-classes/test.schedule", printStream).run();
		String testOutput = new String(Files.readAllBytes(Path.of("target/test-classes/test.output")));
		assertEquals("Sample output vs Generated output", testOutput, outContent.toString());
	}

}
