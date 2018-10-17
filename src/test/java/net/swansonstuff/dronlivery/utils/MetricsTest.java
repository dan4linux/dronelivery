package net.swansonstuff.dronlivery.utils;

import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import net.swansonstuff.dronlivery.delivery.CustomerType;
import net.swansonstuff.dronlivery.delivery.Delivery;

public class MetricsTest {
	
	@Mock
	Delivery delivery;
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Metrics.getInstance().reset();
    }

	@Test
	public void trackTest() {
		int runTimes = 10;
		Metrics metrics = Metrics.getInstance();
		when(delivery.getCustomerType()).thenReturn(CustomerType.PROMOTER);
		for (int i = runTimes; i > 0; i--) {
			metrics.track(delivery);
		}
		assertEquals("Counts promoters", runTimes, metrics.promoters.get());
		when(delivery.getCustomerType()).thenReturn(CustomerType.DETRACTOR);
		for (int i = runTimes; i > 0; i--) {
			metrics.track(delivery);
		}
		assertEquals("Counts detractors", runTimes, metrics.detractors.get());
		when(delivery.getCustomerType()).thenReturn(CustomerType.PROMOTER);
		for (int i = runTimes; i > 0; i--) {
			metrics.track(delivery);
		}
		assertEquals("Counts totals", runTimes * 2, metrics.promoters.get());
	}
}
