package spectrum.kmk.cluster;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.junit.Test;

import spectrum.kmk.cluster.FixtureClusterer.NormalizedClusterable;
import spectrum.kmk.cluster.FixtureClusterer.Normalizer;
import spectrum.kmk.liga.Fixture;
import spectrum.kmk.liga.Team;
import spectrum.kmk.service.Clusterer;

public class FixtureClustererTest {
	@Test
	public void testClusterize() throws Exception {
		final Clusterer<Fixture> clusteriser = new FixtureClusterer();

		final List<Fixture> fixtures = new ArrayList<>();

		fixtures.add(createFixture(1, 2));
		fixtures.add(createFixture(2, 2));
		fixtures.add(createFixture(1, 3));
		fixtures.add(createFixture(0, 2));
		fixtures.add(createFixture(1, 0));
		fixtures.add(createFixture(1, 0));

		clusteriser.clusterize(fixtures);
	}

	@Test
	public void testNormalizeClusterable() {
		final FixtureClusterer clusteriser = new FixtureClusterer();
		final Clusterable clusterable1 = new DoublePoint(new double[] { 0, 0 });
		final Clusterable clusterable2 = new DoublePoint(new double[] { 70, 6 });
		final Clusterable clusterable3 = new DoublePoint(new double[] { 100, 10 });

		final List<Clusterable> input = Arrays.asList(clusterable1, clusterable2, clusterable3);

		final List<NormalizedClusterable<Clusterable>> normalizedList = clusteriser.normalizeClusterable(input);
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
		final FixtureClusterer clusteriser = new FixtureClusterer();

		final double[] point = { 100, 1 };
		final Clusterable clusterable = new DoublePoint(point);
		final Normalizer<Double> normalizer0 = new Normalizer<>(0d, 100d, 1d);
		final Normalizer<Double> normalizer1 = new Normalizer<>(0d, 5d, 1d);

		final List<Normalizer<Double>> normalizerList = Arrays.asList(normalizer0, normalizer1);
		final Clusterable normalized = clusteriser.normalizeDimension(clusterable, 1, normalizerList);
		assertEquals(1, normalized.getPoint()[0], 0.001d);
		assertEquals(0.20, normalized.getPoint()[1], 0.001d);
	}

	@Test
	public void testNormalizer() throws Exception {
		final Normalizer<Long> normalizer = new Normalizer<>(0l, 10000l, 100l);

		BigDecimal normalized;
		normalized = normalizer.apply(5000l);
		assertEquals(50, normalized.intValue());

		normalized = normalizer.apply(Long.valueOf(5));
		assertEquals(BigDecimal.valueOf(0.05), normalized);

		normalized = normalizer.apply(Long.valueOf(35));
		assertEquals(BigDecimal.valueOf(0.35), normalized);

	}

	private Fixture createFixture(final int goalsHome, final int goalsAway) {

		final Team home = new Team("TEAM_HOME", 1000l);
		final Team away = new Team("TEAM_AWAY", 200000l);
		final Fixture fixture = new Fixture(home, away, goalsHome, goalsAway);
		return fixture;
	}

}
