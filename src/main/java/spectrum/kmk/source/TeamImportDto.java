package spectrum.kmk.source;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamImportDto {
	private final String name;
	private final String squadMarketValue;

	@JsonCreator
	TeamImportDto(@JsonProperty("name") final String name, @JsonProperty("squadMarketValue") final String squadMarketValue) {
		this.name = name;
		this.squadMarketValue = squadMarketValue;
	}

	public String getName() {
		return name;
	}

	public String getSquadMarketValue() {
		return squadMarketValue;
	}

}
