package spectrum.kmk.liga;

class TableEntry {

	static class Builder {
		private FixtureTeamResult result;
		private Integer points;
		private Integer fixturesWon;
		private Integer fixturesDraw;
		private Integer fixturesLost;

		TableEntry build() {
			final TableEntry entry = new TableEntry(result, points, //
					fixturesWon, fixturesDraw, fixturesLost);
			return entry;

		}

		Builder withFixturesDraw(final Integer fixturesDraw) {
			this.fixturesDraw = fixturesDraw;
			return this;
		}

		Builder withFixturesLost(final Integer fixturesLost) {
			this.fixturesLost = fixturesLost;
			return this;
		}

		Builder withFixturesWon(final Integer fixturesWon) {
			this.fixturesWon = fixturesWon;
			return this;
		}

		Builder withPoints(final Integer points) {
			this.points = points;
			return this;
		}

		Builder withResult(final FixtureTeamResult result) {
			this.result = result;
			return this;
		}

	}

	static TableEntry buildEmpty(final Team team) {
		final Builder builder = new Builder();
		final FixtureTeamResult result = new FixtureTeamResult(team, 0, 0);
		builder.withFixturesDraw(0).//
				withFixturesLost(0).//
				withFixturesWon(0).//
				withPoints(0).//
				withResult(result);
		return builder.build();
	}

	private final FixtureTeamResult result;
	private final Integer points;
	private final Integer fixturesWon;
	private final Integer fixturesDraw;
	private final Integer fixturesLost;

	private TableEntry(final FixtureTeamResult result, final Integer points, final Integer fixturesWon,
			final Integer fixturesDraw, final Integer fixturesLost) {
		this.result = result;
		this.points = points;
		this.fixturesWon = fixturesWon;
		this.fixturesDraw = fixturesDraw;
		this.fixturesLost = fixturesLost;
	}

	Integer getFixturesDraw() {
		return fixturesDraw;
	}

	Integer getFixturesLost() {
		return fixturesLost;
	}

	Integer getFixturesWon() {
		return fixturesWon;
	}

	Integer getPoints() {
		return points;
	}

	FixtureTeamResult getResult() {
		return result;
	}

}
