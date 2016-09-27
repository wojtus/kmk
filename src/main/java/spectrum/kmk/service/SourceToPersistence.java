package spectrum.kmk.service;

import java.util.function.Function;

import spectrum.kmk.persistence.FixtureBo;
import spectrum.kmk.persistence.TeamBo;
import spectrum.kmk.source.FixtureImportDto;
import spectrum.kmk.source.SMVParser;
import spectrum.kmk.source.TeamImportDto;

class SourceToPersistence {

	private final Function<String, Long> smvParser = new SMVParser();

	FixtureBo createFixture(final FixtureImportDto importDto, final TeamBo home, final TeamBo away) {

		final int goalsHome = importDto.getResult().getGoalsHomeTeam();
		final int goalsAway = importDto.getResult().getGoalsAwayTeam();
		final FixtureBo result = new FixtureBo();
		result.setGoalsAway(goalsAway);
		result.setGoalsHome(goalsHome);
		result.setAway(away);
		result.setHome(home);
		return result;

	}

	TeamBo createTeam(final TeamImportDto teamImportDto) {
		final String name = teamImportDto.getName();
		final String marketValue = teamImportDto.getSquadMarketValue();
		final Long value = smvParser.apply(marketValue);
		final TeamBo teamBo = new TeamBo();
		teamBo.setName(name);
		teamBo.setValue(value);
		return teamBo;

	}

}
