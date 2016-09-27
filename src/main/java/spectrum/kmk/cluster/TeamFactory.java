package spectrum.kmk.cluster;

import java.util.function.Function;

import spectrum.kmk.liga.Team;
import spectrum.kmk.source.SMVParser;
import spectrum.kmk.source.TeamImportDto;

public class TeamFactory {

	private final Function<String, Long> smvParser = new SMVParser();

	Team create(final TeamImportDto teamImportDto) {
		final String name = teamImportDto.getName();
		final String marketValue = teamImportDto.getSquadMarketValue();
		final Long value = smvParser.apply(marketValue);
		final Team team = new Team(name, value);
		return team;

	}

}
