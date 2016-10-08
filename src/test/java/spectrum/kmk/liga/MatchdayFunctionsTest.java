package spectrum.kmk.liga;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

public class MatchdayFunctionsTest {
	MatchdayFunctions matchdayFunctions = new MatchdayFunctions();

	@Test
	public void testCalculateResult() throws Exception {
		final Team homeFixture1 = new Team("TEAM_NAME_HF1", Long.valueOf(10));
		final Team awayFixture1 = new Team("TEAM_NAME_AF1", Long.valueOf(10));
		final Team homeFixture2 = new Team("TEAM_NAME_HF2", Long.valueOf(10));
		final Team awayFixture2 = new Team("TEAM_NAME_AF2", Long.valueOf(10));
		final int goalsHome = 1;
		final int goalsAway = 2;
		final Fixture fixture1 = createTestFixture(homeFixture1, awayFixture1, goalsHome, goalsAway);
		final Fixture fixture2 = createTestFixture(homeFixture2, awayFixture2, goalsHome, goalsAway);
		final List<Fixture> fixtures = Arrays.asList(fixture1, fixture2);
		final Matchday matchday = new Matchday(fixtures);
		final List<FixtureTeamResult> result = matchdayFunctions.calculateResult(matchday);

		final Optional<FixtureTeamResult> homeFixture1Entry = result.stream().//
				filter(e -> e.getTeam().equals(homeFixture1)).findAny();

		final Optional<FixtureTeamResult> homeFixture2Entry = result.stream().//
				filter(e -> e.getTeam().equals(homeFixture2)).findAny();

		final Optional<FixtureTeamResult> awayFixture1Entry = result.stream().//
				filter(e -> e.getTeam().equals(awayFixture1)).findAny();

		final Optional<FixtureTeamResult> awayFixture2Entry = result.stream().//
				filter(e -> e.getTeam().equals(awayFixture2)).findAny();

		Assert.assertEquals(Integer.valueOf(1), homeFixture1Entry.get().getGoalsShot());
		Assert.assertEquals(Integer.valueOf(2), homeFixture1Entry.get().getGoalsLost());
		Assert.assertEquals(Integer.valueOf(1), homeFixture2Entry.get().getGoalsShot());
		Assert.assertEquals(Integer.valueOf(2), homeFixture2Entry.get().getGoalsLost());

		Assert.assertEquals(Integer.valueOf(2), awayFixture1Entry.get().getGoalsShot());
		Assert.assertEquals(Integer.valueOf(1), awayFixture1Entry.get().getGoalsLost());
		Assert.assertEquals(Integer.valueOf(2), awayFixture2Entry.get().getGoalsShot());
		Assert.assertEquals(Integer.valueOf(1), awayFixture2Entry.get().getGoalsLost());

	}

	@Test
	public void testFindPlayedFixture() throws Exception {
		final Team homeFixture1 = new Team("TEAM_NAME_HF1", Long.valueOf(10));
		final Team awayFixture1 = new Team("TEAM_NAME_AF1", Long.valueOf(10));
		final Team homeFixture2 = new Team("TEAM_NAME_HF2", Long.valueOf(10));
		final Team awayFixture2 = new Team("TEAM_NAME_AF2", Long.valueOf(10));
		final int goalsHome = 1;
		final int goalsAway = 2;
		final Fixture fixture1 = createTestFixture(homeFixture1, awayFixture1, goalsHome, goalsAway);
		final Fixture fixture2 = createTestFixture(homeFixture2, awayFixture2, goalsHome, goalsAway);
		final List<Fixture> fixtures = Arrays.asList(fixture1, fixture2);

		Assert.assertEquals(fixture1, matchdayFunctions.findPlayedFixture(fixtures, homeFixture1));
		Assert.assertEquals(fixture1, matchdayFunctions.findPlayedFixture(fixtures, awayFixture1));

		Assert.assertEquals(fixture2, matchdayFunctions.findPlayedFixture(fixtures, homeFixture2));
		Assert.assertEquals(fixture2, matchdayFunctions.findPlayedFixture(fixtures, awayFixture2));
	}

	@Test
	public void testGetGoalsLost() throws Exception {
		final Team home = new Team("TEAM_NAME_H", Long.valueOf(10));
		final Team away = new Team("TEAM_NAME_A", Long.valueOf(10));
		final int goalsHome = 1;
		final int goalsAway = 2;
		final Fixture fixture = createTestFixture(home, away, goalsHome, goalsAway);
		Assert.assertEquals(Integer.valueOf(2), matchdayFunctions.getGoalsLost(fixture, home));
		Assert.assertEquals(Integer.valueOf(1), matchdayFunctions.getGoalsLost(fixture, away));
	}

	@Test
	public void testGetGoalsShot() throws Exception {
		final Team home = new Team("TEAM_NAME_H", Long.valueOf(10));
		final Team away = new Team("TEAM_NAME_A", Long.valueOf(10));
		final int goalsHome = 1;
		final int goalsAway = 2;
		final Fixture fixture = createTestFixture(home, away, goalsHome, goalsAway);
		Assert.assertEquals(Integer.valueOf(1), matchdayFunctions.getGoalsShot(fixture, home));
		Assert.assertEquals(Integer.valueOf(2), matchdayFunctions.getGoalsShot(fixture, away));
	}

	@Test
	public void testIsHomeIsAwayTeam() throws Exception {
		final int goalsHome = 1;
		final int goalsAway = 2;
		final Team home = new Team("TEAM_NAME_H", Long.valueOf(10));
		final Team away = new Team("TEAM_NAME_A", Long.valueOf(10));
		final Fixture fixture = createTestFixture(home, away, goalsHome, goalsAway);

		Assert.assertFalse(matchdayFunctions.isAwayTeam(fixture, home));
		Assert.assertTrue(matchdayFunctions.isHomeTeam(fixture, home));
	}

	private Fixture createTestFixture(final Team home, final Team away, final int goalsHome, final int goalsAway) {
		return new Fixture(home, away, goalsHome, goalsAway);
	}

}
