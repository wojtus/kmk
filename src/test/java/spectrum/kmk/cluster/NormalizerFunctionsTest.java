package spectrum.kmk.cluster;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.junit.Test;

public class NormalizerFunctionsTest {
	private final NormalizerFunctions normalizerFunctions = new NormalizerFunctions();

	@Test
	public void testNormalizeClusterable() {
		final Clusterable clusterable1 = new DoublePoint(new double[] { 0, 0 });
		final Clusterable clusterable2 = new DoublePoint(new double[] { 70, 6 });
		final Clusterable clusterable3 = new DoublePoint(new double[] { 100, 10 });

		final List<Clusterable> input = Arrays.asList(clusterable1, clusterable2, clusterable3);

		final List<BusinessClusterable<Clusterable>> normalizedList = normalizerFunctions.normalizeClusterable(input);
		assertEquals(3, normalizedList.size());

		assertEquals(0d, normalizedList.get(0).getPoint()[0], 0.001);
		assertEquals(0d, normalizedList.get(0).getPoint()[1], 0.001);

		assertEquals(0.7, normalizedList.get(1).getPoint()[0], 0.001);
		assertEquals(0.6, normalizedList.get(1).getPoint()[1], 0.001);

		assertEquals(1, normalizedList.get(2).getPoint()[0], 0.001);
		assertEquals(1, normalizedList.get(2).getPoint()[1], 0.001);

	}

	@Test
	public void testNormalizeDimension() {
		final double[] point = { 100, 1 };
		final Clusterable clusterable = new DoublePoint(point);
		final Normalizer<Double> normalizer0 = new Normalizer<>(0d, 100d, 1d);
		final Normalizer<Double> normalizer1 = new Normalizer<>(0d, 5d, 1d);

		final List<Normalizer<Double>> normalizerList = Arrays.asList(normalizer0, normalizer1);
		final Clusterable normalized = normalizerFunctions.normalizeDimension(clusterable, 1, normalizerList);
		assertEquals(1, normalized.getPoint()[0], 0.001d);
		assertEquals(0.20, normalized.getPoint()[1], 0.001d);
	}
}
