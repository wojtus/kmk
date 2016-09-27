package spectrum.kmk.liga;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MatchdayFunctions {

	List<FixtureTeamResult> calculateResult(final List<Fixture> fixtures) {
		final Stream<Team> awayTeams = fixtures.stream().map(Fixture::getAway);
		final Stream<Team> homeTeams = fixtures.stream().map(Fixture::getHome);
		final Stream<Team> teams = Stream.concat(awayTeams, homeTeams);

		final List<FixtureTeamResult> entries = teams.map(team -> this.createTableEntry(fixtures, team)).//
				collect(Collectors.toList());

		return entries;

	}

	Fixture findPlayedFixture(final List<Fixture> fixtures, final Team team) {

		final Optional<Fixture> fixture = fixtures.stream()
				.filter(f -> f.getHome().equals(team) || f.getAway().equals(team)).findAny();

		return fixture.orElseThrow(() -> new IllegalStateException("Team spielt nicht"));
	}

	Integer getGoalsLost(final Fixture fixture, final Team team) {
		if (isHomeTeam(fixture, team)) {
			return fixture.getGoalsAway();
		}

		if (isAwayTeam(fixture, team)) {
			return fixture.getGoalsHome();
		}
		throw new IllegalStateException("Fixture ohne Team");
	}

	Integer getGoalsShot(final Fixture fixture, final Team team) {
		if (isAwayTeam(fixture, team)) {
			return fixture.getGoalsAway();
		}

		if (isHomeTeam(fixture, team)) {
			return fixture.getGoalsHome();
		}
		throw new IllegalStateException("Fixture ohne Team");
	}

	boolean isAwayTeam(final Fixture fixture, final Team team) {
		return fixture != null && fixture.getAway().equals(team);
	}

	boolean isHomeTeam(final Fixture fixture, final Team team) {
		return fixture != null && fixture.getHome().equals(team);
	}

	private FixtureTeamResult createTableEntry(final List<Fixture> fixtures, final Team team) {
		final Fixture f = findPlayedFixture(fixtures, team);
		final Integer goalsShot = getGoalsShot(f, team);
		final Integer goalsLost = getGoalsLost(f, team);
		final FixtureTeamResult fixtureTeamResult = new FixtureTeamResult(team, goalsShot, goalsLost);
		return fixtureTeamResult;
	}

}