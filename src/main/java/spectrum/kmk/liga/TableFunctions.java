package spectrum.kmk.liga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TableFunctions {

	private final TableEntryFunctions tableEntryFunctions = new TableEntryFunctions();
	private final MatchdayFunctions matchdayFunctions = new MatchdayFunctions();

	Table createEmptyTable(final Collection<Team> teams) {
		final List<TableEntry> tableEntries = new ArrayList<>();
		for (final Team team : teams) {
			final TableEntry entry = TableEntry.buildEmpty(team);

			tableEntries.add(entry);
		}
		return new Table(tableEntries);
	}

	Table createFinalTable(final List<Matchday> matchdays) {
		final Collection<Team> teams = getTeams(matchdays);
		final Table emptyTable = createEmptyTable(teams);

		final List<Table> tables = new ArrayList<>();
		tables.add(emptyTable);

		for (final Matchday matchday : matchdays) {
			final Table tableOfMachday = calculateMatchday(matchday, tables.get(tables.size() - 1));
			tables.add(tableOfMachday);

		}

		return tables.get(tables.size() - 1);

	}

	private Table calculateMatchday(final Matchday matchday, final Table previousTable) {
		final List<FixtureTeamResult> matchdayResult = matchdayFunctions.calculateResult(matchday);
		final List<TableEntry> tableEntries = new ArrayList<>();

		for (final FixtureTeamResult fixtureTeamResult : matchdayResult) {
			final TableEntry previousEntry = getPreviouseEntry(fixtureTeamResult, previousTable);
			final TableEntry newEntry = tableEntryFunctions.calculateNewEntry(previousEntry, fixtureTeamResult);
			tableEntries.add(newEntry);

		}
		final Comparator<TableEntry> comparatror = createComparator();
		Collections.sort(tableEntries, comparatror);
		return new Table(tableEntries);
	}

	private Comparator<TableEntry> createComparator() {
		final Comparator<TableEntry> points = Comparator.comparingInt(TableEntry::getPoints);
		final Comparator<TableEntry> fixturesWon = Comparator.comparingInt(TableEntry::getFixturesWon);
		final Comparator<TableEntry> fixturesDraw = Comparator.comparingInt(TableEntry::getFixturesDraw);
		final Comparator<TableEntry> fixturesLost = Comparator.comparingInt(TableEntry::getFixturesLost);

		final Comparator<TableEntry> goalsShot = Comparator.comparingInt(te -> te.getResult().getGoalsShot());
		final Comparator<TableEntry> goalsLost = Comparator.comparingInt(te -> te.getResult().getGoalsLost());
		final Comparator<TableEntry> combinedComparator = points.thenComparing(fixturesWon).thenComparing(fixturesDraw)
				.thenComparing(fixturesLost.reversed()).thenComparing(goalsShot).thenComparing(goalsLost.reversed());
		return combinedComparator.reversed();
	}

	private TableEntry getPreviouseEntry(final FixtureTeamResult fixtureTeamResult, final Table table) {

		final Stream<Team> teams = table.getTableEntries().stream().//
				map(TableEntry::getResult).//
				map(FixtureTeamResult::getTeam);

		final Optional<Team> team = teams.filter(t -> t.equals(fixtureTeamResult.getTeam())).findAny();

		final Predicate<TableEntry> isTableEntryOfTeam = te -> {
			final Team teamOfResult = te.getResult().getTeam();
			return Objects.equals(teamOfResult, team.get());
		};
		final Optional<TableEntry> optional = table.getTableEntries().stream().filter(isTableEntryOfTeam).findAny();

		return optional.get();
	}

	private Stream<Team> getStreamFromFixture(final Fixture fixture) {
		return Stream.of(fixture.getHome(), fixture.getAway());
	}

	private Collection<Team> getTeams(final List<Matchday> matchdays) {

		final Set<Team> teams = matchdays.stream().//
				flatMap(matchday -> matchday.getFixtures().stream()).//
				flatMap(this::getStreamFromFixture).//
				collect(Collectors.toSet());
		return teams;
	}

}
