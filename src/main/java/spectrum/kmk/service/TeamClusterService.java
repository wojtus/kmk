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
import spectrum.kmk.cluster.TeamClusterable;
import spectrum.kmk.liga.Team;
import spectrum.kmk.persistence.TeamBo;
import spectrum.kmk.persistence.TeamRepository;

@Service
class TeamClusterService implements ClusterService<TeamClusterable> {

	private final PersistenceToLiga persistenceToLiga = new PersistenceToLiga();
	private final TeamRepository teamRepository;
	final Clusterer<Team, TeamClusterable> teamClusterer;

	@Autowired
	TeamClusterService(final TeamRepository teamRepository, final Clusterer<Team, TeamClusterable> teamClusterer) {
		this.teamRepository = teamRepository;
		this.teamClusterer = teamClusterer;
	}

	@Override
	public List<CentroidCluster<BusinessClusterable<TeamClusterable>>> clusterObjects() {

		final List<TeamBo> teams = new LinkedList<>();
		teamRepository.findAll().forEach(teams::add);
		final Collection<Team> ligaDtos = teams.stream().//
				map(persistenceToLiga::createTeam).//
				collect(Collectors.toList());
		final List<CentroidCluster<BusinessClusterable<TeamClusterable>>> cluster = teamClusterer.clusterize(ligaDtos);
		return cluster;
	}

}
