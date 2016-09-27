package spectrum.kmk.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spectrum.kmk.liga.Team;
import spectrum.kmk.persistence.TeamBo;
import spectrum.kmk.persistence.TeamRepository;

@Service
class TeamClusterService {

	private final PersistenceToLiga persistenceToLiga = new PersistenceToLiga();
	private final TeamRepository teamRepository;
	final Clusterer<Team> teamClusterer;

	@Autowired
	TeamClusterService(final TeamRepository teamRepository, final Clusterer<Team> teamClusterer) {
		this.teamRepository = teamRepository;
		this.teamClusterer = teamClusterer;
	}

	void clusterTeams() {
		final List<TeamBo> teams = new LinkedList<>();
		teamRepository.findAll().forEach(teams::add);
		final Collection<Team> ligaDtos = teams.stream().//
				map(persistenceToLiga::createTeam).//
				collect(Collectors.toList());
		teamClusterer.clusterize(ligaDtos);

	}

}
