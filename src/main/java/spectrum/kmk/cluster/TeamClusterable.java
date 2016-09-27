package spectrum.kmk.cluster;

import org.apache.commons.math3.ml.clustering.Clusterable;

import spectrum.kmk.liga.Team;

public class TeamClusterable implements Clusterable {

	private final Team team;

	TeamClusterable(final Team team) {
		this.team = team;
	}

	@Override
	public double[] getPoint() {

		final long smv = getTeam().getValue();
		final double smvDouble = Long.valueOf(smv).doubleValue();
		final double[] result = { smvDouble };
		return result;
	}

	public Team getTeam() {
		return team;
	}

}
