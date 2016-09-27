package spectrum.kmk.source;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixtureImportDto {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Result {
		private final Integer goalsHomeTeam;
		private final Integer goalsAwayTeam;

		@JsonCreator
		Result(@JsonProperty("goalsHomeTeam") final Integer goalsHomeTeam,
				@JsonProperty("goalsAwayTeam") final Integer goalsAwayTeam) {
			this.goalsHomeTeam = goalsHomeTeam;
			this.goalsAwayTeam = goalsAwayTeam;
		}

		public Integer getGoalsAwayTeam() {
			return goalsAwayTeam;
		}

		public Integer getGoalsHomeTeam() {
			return goalsHomeTeam;
		}

	}

	private final String homeTeamName;
	private final String awayTeamName;
	private final Result result;
	private final Integer matchday;

	@JsonCreator
	FixtureImportDto(@JsonProperty("homeTeamName") final String homeTeamName,
			@JsonProperty("awayTeamName") final String awayTeamName, //
			@JsonProperty("result") final Result result, //
			@JsonProperty("matchday") final Integer matchday) {

		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.result = result;
		this.matchday = matchday;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public Integer getMatchday() {
		return matchday;
	}

	public Result getResult() {
		return result;
	}

}
