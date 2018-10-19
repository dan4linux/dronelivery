/**
 * 
 */
package net.swansonstuff.dronlivery.delivery.algorithms;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dan Swanson (dan4linux@gmail.com)
 *
 */
public class AlgorithmManager {

	private static final Logger LOG = LoggerFactory.getLogger(AlgorithmManager.class);
	private static final Algorithm DEFAULT_ALGORITHM = OrderTimeOnly.getInstance();
	private static final AlgorithmManager instance = new AlgorithmManager();

	private final Set<Algorithm> algorithms = new HashSet<>();

	private AlgorithmManager() {
		Reflections reflections = new Reflections("net.swansonstuff.dronlivery.delivery.algorithms");
			reflections.getSubTypesOf(Algorithm.class).stream().forEach((algClass)->{
				try {
					LOG.info("Loading algorithm: "+algClass.getSimpleName());
					Method method = algClass.getMethod("getInstance");
					Algorithm algorithm = (Algorithm)(method.invoke(null));
					algorithms.add(algorithm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
	}

	public static AlgorithmManager getInstance() {
		return instance;
	}

	public static Algorithm getDefault() {
		return DEFAULT_ALGORITHM;
	}

	/**
	 * @return the algorithms
	 */
	public Set<Algorithm> getAlgorithms() {
		return algorithms;
	}

}
