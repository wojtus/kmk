package spectrum.kmk.source;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TeamReaderTest {

	public static List<TeamImportDto> getTestTeams() {
		final TeamReader teamReader = new TeamReader();

		try (InputStream is = ClassLoader.getSystemResource("1bundesliga-teams.json").openStream();) {
			final List<TeamImportDto> teams = teamReader.getTeams(is);
			return teams;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGetTeams() throws Exception {

		final List<TeamImportDto> teams = getTestTeams();
		Assert.assertNotNull(teams);
		Assert.assertEquals(18, teams.size());
	}

}
