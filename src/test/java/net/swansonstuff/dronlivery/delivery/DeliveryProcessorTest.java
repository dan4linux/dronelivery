package net.swansonstuff.dronlivery.delivery;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

import org.junit.*;

import net.swansonstuff.dronlivery.delivery.algorithms.*;
import net.swansonstuff.dronlivery.drones.Drone;
import net.swansonstuff.dronlivery.drones.DronePool;
import net.swansonstuff.dronlivery.utils.Metrics;

public class DeliveryProcessorTest {

	static AlgorithmManager algorithmManager = mock(AlgorithmManager.class);

	static HashSet<Algorithm> algorithms = new HashSet<>();

	@BeforeClass
	public static void setup() {
		new DeliveryProcessor("", null);
		algorithms.add(TransitTimeOnly.getInstance());
		when(algorithmManager.getAlgorithms()).thenReturn(algorithms);
	}

	@Before
	public void setupEachTime() {
		Metrics.getInstance().reset();
		DeliveryManager.getInstance().reset();
		DronePool.getInstance().clearPool();
		new Drone().free();
	}


	@Test
	public void runTest() throws IOException {
		assertTrue("Empty deliveries queue", DeliveryManager.getInstance().deliveries.isEmpty());
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();

		new DeliveryProcessor("target/test-classes/test.schedule", new PrintStream(outContent)).run(algorithmManager);
		String testOutput = new String(Files.readAllBytes(Paths.get("target/test-classes/test.output")));
		System.err.println("test:\n"+testOutput);
		System.err.println("gen:\n"+outContent.toString());
		assertEquals("Sample output vs Generated output", testOutput, outContent.toString());
	}

	@Test
	public void runWithBadData() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		boolean caughtException = false;
		try {
			new DeliveryProcessor("target/test-classes/bad.schedule", new PrintStream(outContent)).run(algorithmManager);
		} catch(Throwable t) {
			caughtException = true;
		}
		assertFalse("Caught exception on bad input", caughtException);
	}

	@Test
	public void runWithMissingFile() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		boolean caughtException = false;
		try {
			new DeliveryProcessor("target/test-classes/nofile.schedule", new PrintStream(outContent)).run(algorithmManager);
		} catch(Throwable t) {
			caughtException = true;
		}
		assertTrue("Caught exception on missing file", caughtException);
	}

}
