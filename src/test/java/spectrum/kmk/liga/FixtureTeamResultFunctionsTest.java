package spectrum.kmk.liga;

import org.junit.Assert;
import org.junit.Test;

public class FixtureTeamResultFunctionsTest {

	private final FixtureTeamResultFunctions functions = new FixtureTeamResultFunctions();

	@Test
	public void testCalculatePoints() throws Exception {

		final FixtureTeamResult tableEntryDraw = createTestEntry(1, 1);
		Assert.assertEquals(Integer.valueOf(1), functions.calculatePoints(tableEntryDraw));
		final FixtureTeamResult tableEntryWon = createTestEntry(2, 1);
		Assert.assertEquals(Integer.valueOf(3), functions.calculatePoints(tableEntryWon));
		final FixtureTeamResult tableEntryLost = createTestEntry(2, 3);
		Assert.assertEquals(Integer.valueOf(0), functions.calculatePoints(tableEntryLost));

	}

	@Test
	public void testIsGameDraw() throws Exception {

		final FixtureTeamResult tableEntryDraw = createTestEntry(1, 1);
		Assert.assertTrue(functions.isGameDraw(tableEntryDraw));
		final FixtureTeamResult tableEntryWon = createTestEntry(2, 1);
		Assert.assertFalse(functions.isGameDraw(tableEntryWon));
		final FixtureTeamResult tableEntryLost = createTestEntry(2, 3);
		Assert.assertFalse(functions.isGameDraw(tableEntryLost));

	}

	@Test
	public void testIsGameLost() throws Exception {
		final FixtureTeamResult tableEntryDraw = createTestEntry(1, 1);
		Assert.assertFalse(functions.isGameLost(tableEntryDraw));
		final FixtureTeamResult tableEntryWon = createTestEntry(2, 1);
		Assert.assertFalse(functions.isGameLost(tableEntryWon));
		final FixtureTeamResult tableEntryLost = createTestEntry(2, 3);
		Assert.assertTrue(functions.isGameLost(tableEntryLost));
	}

	@Test
	public void testIsGameWon() throws Exception {
		final FixtureTeamResult tableEntryDraw = createTestEntry(1, 1);
		Assert.assertFalse(functions.isGameWon(tableEntryDraw));
		final FixtureTeamResult tableEntryWon = createTestEntry(2, 1);
		Assert.assertTrue(functions.isGameWon(tableEntryWon));
		final FixtureTeamResult tableEntryLost = createTestEntry(2, 3);
		Assert.assertFalse(functions.isGameWon(tableEntryLost));
	}

	private FixtureTeamResult createTestEntry(final int shot, final int lost) {
		final Team team = new Team("TEAM", Long.MAX_VALUE);
		final FixtureTeamResult fixtureTeamResult = new FixtureTeamResult(team, Integer.valueOf(shot),
				Integer.valueOf(lost));
		return fixtureTeamResult;
	}

}
