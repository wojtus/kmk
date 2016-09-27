package spectrum.kmk.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spectrum.kmk.cluster.BusinessClusterable;
import spectrum.kmk.cluster.Clusterer;
import spectrum.kmk.cluster.FixtureClusterable;
import spectrum.kmk.liga.Fixture;
import spectrum.kmk.persistence.FixtureBo;
import spectrum.kmk.persistence.FixtureRepository;

@Service
class FixtureClusterService implements ClusterService<FixtureClusterable> {

	private final PersistenceToLiga persistenceToLiga = new PersistenceToLiga();
	private final FixtureRepository fixtureRepository;
	private final Clusterer<Fixture, FixtureClusterable> fixtureClusterer;

	@Autowired
	FixtureClusterService(final FixtureRepository fixtureRepository,
			final Clusterer<Fixture, FixtureClusterable> fixtureClusterer) {
		this.fixtureRepository = fixtureRepository;
		this.fixtureClusterer = fixtureClusterer;
	}

	@Override
	public List<CentroidCluster<BusinessClusterable<FixtureClusterable>>> clusterObjects() {
		final List<FixtureBo> teams = new LinkedList<>();

		fixtureRepository.findAll().forEach(teams::add);
		final Collection<Fixture> ligaDtos = teams.stream().//
				map(persistenceToLiga::createFixture).//
				collect(Collectors.toList());
		final List<CentroidCluster<BusinessClusterable<FixtureClusterable>>> cluster = fixtureClusterer
				.clusterize(ligaDtos);
		return cluster;

	}

}
