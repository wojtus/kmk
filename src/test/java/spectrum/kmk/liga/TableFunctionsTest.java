package spectrum.kmk.liga;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class TableFunctionsTest {
	private static final String TEAM_A = "TeamA";
	private static final String TEAM_B = "TeamB";
	private static final String TEAM_C = "TeamC";
	private static final String TEAM_D = "TeamD";
	private final TableFunctions tableFunctions = new TableFunctions();

	@Test
	public void testCreateEmptyTable() throws Exception {
		final Team teamA = new Team(TEAM_A, 1l);
		final Team teamB = new Team(TEAM_B, 1l);
		final Collection<Team> teams = Arrays.asList(teamA, teamB);
		final Table emptyTable = tableFunctions.createEmptyTable(teams);
		final List<TableEntry> tableEntries = emptyTable.getTableEntries();
		assertEquals(2, tableEntries.size());

		assertEquals(2, tableEntries.size());
		assertEmptyEntry(tableEntries.get(0), TEAM_A);
		assertEmptyEntry(tableEntries.get(1), TEAM_B);
	}

	@Test
	public void testCreateFinalTable() throws Exception {

		final Team teamA = new Team(TEAM_A, 1l);
		final Team teamB = new Team(TEAM_B, 1l);
		final Team teamC = new Team(TEAM_C, 1l);
		final Team teamD = new Team(TEAM_D, 1l);
		final Fixture fitureA = new Fixture(teamA, teamB, 3, 1);
		final Fixture fixtureB = new Fixture(teamC, teamD, 2, 0);
		final List<Fixture> fixtures = Arrays.asList(fitureA, fixtureB);
		final List<Matchday> matchdays = Arrays.asList(new Matchday(fixtures));
		final Table finalTable = tableFunctions.createFinalTable(matchdays);
		final List<TableEntry> tableEntries = finalTable.getTableEntries();
		assertEquals(4, tableEntries.size());

		final TableEntry first = tableEntries.get(0);
		assertEquals(TEAM_A, first.getResult().getTeam().getName());
		assertEquals(0, first.getFixturesDraw().intValue());
		assertEquals(0, first.getFixturesLost().intValue());
		assertEquals(1, first.getFixturesWon().intValue());
		assertEquals(3, first.getPoints().intValue());
		assertEquals(1, first.getResult().getGoalsLost().intValue());
		assertEquals(3, first.getResult().getGoalsShot().intValue());

		final TableEntry second = tableEntries.get(1);
		assertEquals(TEAM_C, second.getResult().getTeam().getName());
		assertEquals(0, second.getFixturesDraw().intValue());
		assertEquals(0, second.getFixturesLost().intValue());
		assertEquals(1, second.getFixturesWon().intValue());
		assertEquals(3, second.getPoints().intValue());
		assertEquals(0, second.getResult().getGoalsLost().intValue());
		assertEquals(2, second.getResult().getGoalsShot().intValue());

		final TableEntry third = tableEntries.get(2);
		assertEquals(TEAM_B, third.getResult().getTeam().getName());
		assertEquals(0, third.getFixturesDraw().intValue());
		assertEquals(1, third.getFixturesLost().intValue());
		assertEquals(0, third.getFixturesWon().intValue());
		assertEquals(0, third.getPoints().intValue());
		assertEquals(3, third.getResult().getGoalsLost().intValue());
		assertEquals(1, third.getResult().getGoalsShot().intValue());

		final TableEntry last = tableEntries.get(3);
		assertEquals(TEAM_D, last.getResult().getTeam().getName());
		assertEquals(0, last.getFixturesDraw().intValue());
		assertEquals(1, last.getFixturesLost().intValue());
		assertEquals(0, last.getFixturesWon().intValue());
		assertEquals(0, last.getPoints().intValue());
		assertEquals(2, last.getResult().getGoalsLost().intValue());
		assertEquals(0, last.getResult().getGoalsShot().intValue());

	}

	private void assertEmptyEntry(final TableEntry entryA, final String teamName) {
		assertEquals(0, entryA.getFixturesDraw().intValue());
		assertEquals(0, entryA.getFixturesLost().intValue());
		assertEquals(0, entryA.getFixturesWon().intValue());
		assertEquals(0, entryA.getPoints().intValue());
		assertEquals(0, entryA.getResult().getGoalsLost().intValue());
		assertEquals(0, entryA.getResult().getGoalsShot().intValue());
		assertEquals(teamName, entryA.getResult().getTeam().getName());
	}

}
