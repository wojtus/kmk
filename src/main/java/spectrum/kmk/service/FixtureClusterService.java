package spectrum.kmk.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spectrum.kmk.liga.Fixture;
import spectrum.kmk.persistence.FixtureBo;
import spectrum.kmk.persistence.FixtureRepository;

@Service
class FixtureClusterService {

	private final PersistenceToLiga persistenceToLiga = new PersistenceToLiga();
	private final FixtureRepository fixtureRepository;
	private final Clusterer<Fixture> fixtureClusterer;

	@Autowired
	FixtureClusterService(final FixtureRepository fixtureRepository, final Clusterer<Fixture> fixtureClusterer) {
		this.fixtureRepository = fixtureRepository;
		this.fixtureClusterer = fixtureClusterer;
	}

	void clusterTeams() {
		final List<FixtureBo> teams = new LinkedList<>();

		fixtureRepository.findAll().forEach(teams::add);
		final Collection<Fixture> ligaDtos = teams.stream().//
				map(persistenceToLiga::createFixture).//
				collect(Collectors.toList());
		fixtureClusterer.clusterize(ligaDtos);

	}

}
