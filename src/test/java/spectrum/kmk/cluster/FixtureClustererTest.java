package spectrum.kmk.cluster;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import spectrum.kmk.liga.Fixture;
import spectrum.kmk.liga.Team;

public class FixtureClustererTest {
	@Test
	public void testClusterize() throws Exception {
		final Clusterer<Fixture, FixtureClusterable> clusteriser = new FixtureClusterer();

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
