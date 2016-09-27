package spectrum.kmk.liga;

import java.util.Objects;

import spectrum.kmk.liga.TableEntry.Builder;

public class TableEntryFunctions {

	private final FixtureTeamResultFunctions teamResultFunctions = new FixtureTeamResultFunctions();

	TableEntry calculateNewEntry(final TableEntry previousEntry, final FixtureTeamResult result) {
		final FixtureTeamResult combinedResult = calculateCombinedResult(previousEntry.getResult(), result);
		final Integer fixturesDraw = teamResultFunctions.isGameDraw(result) ? //
				previousEntry.getFixturesDraw() + 1 : previousEntry.getFixturesDraw();
		final Integer fixturesLost = teamResultFunctions.isGameLost(result) ? //
				previousEntry.getFixturesLost() + 1 : previousEntry.getFixturesLost();
		final Integer fixturesWon = teamResultFunctions.isGameWon(result) ? //
				previousEntry.getFixturesWon() + 1 : previousEntry.getFixturesWon();
		final Integer points = previousEntry.getPoints() + teamResultFunctions.calculatePoints(result);

		final Builder builder = new TableEntry.Builder();
		builder.withResult(combinedResult).//
				withFixturesDraw(fixturesDraw).//
				withFixturesLost(fixturesLost).//
				withFixturesWon(fixturesWon).//
				withPoints(points);

		return builder.build();

	}

	private FixtureTeamResult calculateCombinedResult(final FixtureTeamResult resultA,
			final FixtureTeamResult resultB) {

		if (!Objects.equals(resultA.getTeam(), resultB.getTeam())) {
			throw new IllegalStateException("Teams müssen gleich sein");
		}
		final Team team = resultA.getTeam();
		final Integer goalsShot = resultA.getGoalsShot() + resultB.getGoalsShot();
		final Integer goalsLost = resultA.getGoalsLost() + resultB.getGoalsLost();
		final FixtureTeamResult combinedResult = new FixtureTeamResult(team, goalsShot, goalsLost);

		return combinedResult;
	}

}
