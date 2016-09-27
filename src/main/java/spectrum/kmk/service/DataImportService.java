package spectrum.kmk.service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spectrum.kmk.persistence.FixtureBo;
import spectrum.kmk.persistence.FixtureRepository;
import spectrum.kmk.persistence.TeamBo;
import spectrum.kmk.persistence.TeamRepository;
import spectrum.kmk.source.FixtureImportDto;
import spectrum.kmk.source.TeamImportDto;

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

	void importData(final Supplier<Collection<TeamImportDto>> teams,
			final Supplier<Collection<FixtureImportDto>> fixtures) throws Exception {
		final Consumer<Collection<TeamImportDto>> importEachTeam = teamDtos -> teamDtos.forEach(this::importTeam);
		final Consumer<Collection<FixtureImportDto>> importEachFixture = fixtureDtos -> fixtureDtos
				.forEach(this::importFixture);
		CompletableFuture.//
				supplyAsync(teams, executor).//
				thenAccept(importEachTeam).//
				join();
		CompletableFuture.//
				supplyAsync(fixtures, executor).//
				thenAccept(importEachFixture).//
				join();
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
