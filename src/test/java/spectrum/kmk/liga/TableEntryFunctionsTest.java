package spectrum.kmk.liga;

import org.junit.Assert;
import org.junit.Test;

public class TableEntryFunctionsTest {

	final TableEntryFunctions entryFunctions = new TableEntryFunctions();

	@Test
	public void testCalculateNewEntry() throws Exception {
		final TableEntryFunctions entryFunctions = new TableEntryFunctions();
		final Team team = new Team("TEAM", Long.valueOf(11));
		TableEntry previousEntry = TableEntry.buildEmpty(team);

		FixtureTeamResult result;

		result = createNewResult(team, 1, 1);
		previousEntry = entryFunctions.calculateNewEntry(previousEntry, result);
		result = createNewResult(team, 2, 1);
		previousEntry = entryFunctions.calculateNewEntry(previousEntry, result);
		result = createNewResult(team, 3, 1);
		previousEntry = entryFunctions.calculateNewEntry(previousEntry, result);
		result = createNewResult(team, 0, 3);
		previousEntry = entryFunctions.calculateNewEntry(previousEntry, result);
		result = createNewResult(team, 0, 3);
		previousEntry = entryFunctions.calculateNewEntry(previousEntry, result);
		result = createNewResult(team, 0, 3);
		previousEntry = entryFunctions.calculateNewEntry(previousEntry, result);

		Assert.assertEquals(1, previousEntry.getFixturesDraw().intValue());
		Assert.assertEquals(2, previousEntry.getFixturesWon().intValue());
		Assert.assertEquals(3, previousEntry.getFixturesLost().intValue());
		Assert.assertEquals(7, previousEntry.getPoints().intValue());
		Assert.assertEquals(12, previousEntry.getResult().getGoalsLost().intValue());
		Assert.assertEquals(6, previousEntry.getResult().getGoalsShot().intValue());

	}

	private FixtureTeamResult createNewResult(final Team team, final Integer goalsShot, final Integer goalsLost) {
		final FixtureTeamResult result = new FixtureTeamResult(team, goalsShot, goalsLost);
		return result;
	}

}
