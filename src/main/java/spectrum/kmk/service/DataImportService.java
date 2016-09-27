package spectrum.kmk.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spectrum.kmk.persistence.FixtureBo;
import spectrum.kmk.persistence.FixtureRepository;
import spectrum.kmk.persistence.TeamBo;
import spectrum.kmk.persistence.TeamRepository;
import spectrum.kmk.source.FixtureImportDto;
import spectrum.kmk.source.FixtureReaderTest;
import spectrum.kmk.source.TeamImportDto;
import spectrum.kmk.source.TeamReaderTest;

@Service
public class DataImportService {
	private final SourceToPersistence sourceToPersistence = new SourceToPersistence();

	private final TeamRepository teamRepository;
	private final FixtureRepository fixtureRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Executor executor;

	@Autowired
	DataImportService(final TeamRepository teamRepository, final FixtureRepository fixtureRepository) {
		this.teamRepository = teamRepository;
		this.fixtureRepository = fixtureRepository;
	}

	void importData() throws Exception {
		final Consumer<List<TeamImportDto>> importEachTeam = teamDtos -> teamDtos.forEach(this::importTeam);
		final Consumer<List<FixtureImportDto>> importEachFixture = fixtureDtos -> fixtureDtos
				.forEach(this::importFixture);
		CompletableFuture.//
				supplyAsync(TeamReaderTest::getTestTeams, executor).//
				thenAccept(importEachTeam).//
				thenApply(this::getTestFixturesFromVoid).//
				thenAccept(importEachFixture).//
				join();
	}

	private List<FixtureImportDto> getTestFixturesFromVoid(final Void v) {
		return FixtureReaderTest.getTestFixtures();

	}

	private void importFixture(final FixtureImportDto fixtureImportDto) {
		final TeamBo home = teamRepository.findByName(fixtureImportDto.getHomeTeamName());
		final TeamBo away = teamRepository.findByName(fixtureImportDto.getAwayTeamName());
		final FixtureBo fixtureBo = fixtureRepository.findByHomeAndAway(home, away);
		logger.debug("Fixture found for " + fixtureBo);
		if (fixtureBo == null) {
			final FixtureBo newFixture = sourceToPersistence.createFixture(fixtureImportDto, home, away);
			final FixtureBo saved = fixtureRepository.save(newFixture);
			logger.debug("Fixture saved " + saved.getId());
		}
	}

	private void importTeam(final TeamImportDto teamImportDto) {
		final TeamBo teamBo = teamRepository.findByName(teamImportDto.getName());
		if (teamBo == null) {
			final TeamBo newTeam = sourceToPersistence.createTeam(teamImportDto);

			final TeamBo saved = teamRepository.save(newTeam);
			logger.debug("Fixture saved " + saved.getId());

		}
	}

}
