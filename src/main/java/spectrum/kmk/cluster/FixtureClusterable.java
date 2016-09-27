package spectrum.kmk.cluster;

import org.apache.commons.math3.ml.clustering.Clusterable;

import spectrum.kmk.liga.Fixture;
import spectrum.kmk.liga.Team;

public class FixtureClusterable implements Clusterable {

	private final Fixture fixture;

	FixtureClusterable(final Fixture fixture) {
		this.fixture = fixture;
	}

	public Fixture getFixture() {
		return fixture;
	}

	@Override
	public double[] getPoint() {
		final Team home = fixture.getHome();
		final Team away = fixture.getAway();
		final double goalsHomeTeam = fixture.getGoalsHome();
		final double goalsAwayTeam = fixture.getGoalsAway();
		final double goalsDifference = Math.abs(goalsHomeTeam - goalsAwayTeam);
		final double[] result = { goalsHomeTeam, goalsAwayTeam, home.getValue() + away.getValue(), goalsDifference };
		return result;
	}

}
