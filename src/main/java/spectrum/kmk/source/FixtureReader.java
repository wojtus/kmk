package spectrum.kmk.source;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

class FixtureReader {

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class FixtureJsonDto {
		private List<FixtureImportDto> fixtures;

		List<FixtureImportDto> getFixtures() {
			return fixtures;
		}

		@SuppressWarnings("unused")
		void setFixtures(final List<FixtureImportDto> fixtures) {
			this.fixtures = fixtures;
		}

	};

	List<FixtureImportDto> getFixtures(final InputStream inputStream) throws Exception {
		final ObjectMapper objectMapper = new ObjectMapper();
		final FixtureJsonDto readValue = objectMapper.readValue(inputStream, FixtureJsonDto.class);
		final List<FixtureImportDto> resultteams = new LinkedList<>(readValue.getFixtures());
		return resultteams;

	}

}
