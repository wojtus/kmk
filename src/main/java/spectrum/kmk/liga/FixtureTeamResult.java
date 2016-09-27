package spectrum.kmk.liga;

public class FixtureTeamResult {

	private final Team team;
	private final Integer goalsShot;
	private final Integer goalsLost;

	FixtureTeamResult(final Team team, final Integer goalsShot, final Integer goalsLost) {
		this.team = team;
		this.goalsShot = goalsShot;
		this.goalsLost = goalsLost;
	}

	Integer getGoalsLost() {
		return goalsLost;
	}

	Integer getGoalsShot() {
		return goalsShot;
	}

	Team getTeam() {
		return team;
	}

}
