package spectrum.kmk.cluster;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import spectrum.kmk.liga.Team;
import spectrum.kmk.source.TeamImportDto;
import spectrum.kmk.source.TeamReaderTest;

public class TeamSMVClustererTest {

	@Test
	public void testClusterize() throws Exception {
		final TeamSMVClusterer clusteriser = new TeamSMVClusterer();
		final List<TeamImportDto> importDtos = TeamReaderTest.getTestTeams();
		final TeamFactory teamFactory = new TeamFactory();

		final List<Team> teams = importDtos.stream().map(teamFactory::create).collect(Collectors.toList());

		clusteriser.clusterize(teams);
	}

}
