package spectrum.kmk.service;

import spectrum.kmk.liga.Fixture;
import spectrum.kmk.liga.Team;
import spectrum.kmk.persistence.FixtureBo;
import spectrum.kmk.persistence.TeamBo;

public class PersistenceToLiga {

	Fixture createFixture(final FixtureBo bo) {
		final Team home = createTeam(bo.getHome());
		final Team away = createTeam(bo.getAway());
		final Fixture fixture = new Fixture(home, away, bo.getGoalsHome(), bo.getGoalsAway());
		return fixture;
	}

	Team createTeam(final TeamBo bo) {
		return new Team(bo.getName(), bo.getValue());
	}

}
