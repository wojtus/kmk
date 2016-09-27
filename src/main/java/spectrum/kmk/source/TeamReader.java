package spectrum.kmk.source;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

class TeamReader implements LigaReader<TeamImportDto> {

	@Override
	public List<TeamImportDto> readObjects() {
		try (InputStream is = ClassLoader.getSystemResource("1bundesliga-teams.json").openStream();) {
			final List<TeamImportDto> teams = getTeams(is);
			return teams;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	List<TeamImportDto> getTeams(final InputStream inputStream) throws Exception {

		final ObjectMapper objectMapper = new ObjectMapper();
		final TeamJsonDto readValue = objectMapper.readValue(inputStream, TeamJsonDto.class);
		final List<TeamImportDto> resultteams = new LinkedList<>(readValue.getTeams());
		return resultteams;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class TeamJsonDto {
		private List<TeamImportDto> teams;

		List<TeamImportDto> getTeams() {
			return teams;
		}

		@SuppressWarnings("unused")
		void setTeams(final List<TeamImportDto> teams) {
			this.teams = teams;
		}

	}

}
