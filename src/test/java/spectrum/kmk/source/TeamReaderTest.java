package spectrum.kmk.source;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TeamReaderTest {

	@Test
	public void testGetTeams() throws Exception {
		final TeamReader teamReader = new TeamReader();
		final List<TeamImportDto> teams = teamReader.readObjects();
		Assert.assertNotNull(teams);
		Assert.assertEquals(18, teams.size());
	}

}
