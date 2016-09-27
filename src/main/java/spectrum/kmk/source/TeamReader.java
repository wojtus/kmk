package spectrum.kmk.source;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

class TeamReader {
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

	};

	List<TeamImportDto> getTeams(final InputStream inputStream) throws Exception {

		final ObjectMapper objectMapper = new ObjectMapper();
		final TeamJsonDto readValue = objectMapper.readValue(inputStream, TeamJsonDto.class);
		final List<TeamImportDto> resultteams = new LinkedList<>(readValue.getTeams());
		return resultteams;

	}

}
