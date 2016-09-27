package spectrum.kmk.liga;

class FixtureTeamResultFunctions {

	Integer calculatePoints(final FixtureTeamResult fixtureTeamResult) {
		if (isGameLost(fixtureTeamResult)) {
			return Integer.valueOf(0);
		}
		if (isGameDraw(fixtureTeamResult)) {
			return Integer.valueOf(1);
		}
		if (isGameWon(fixtureTeamResult)) {
			return Integer.valueOf(3);
		}

		throw new IllegalStateException();
	}

	boolean isGameDraw(final FixtureTeamResult fixtureTeamResult) {
		return result(fixtureTeamResult) == 0;
	}

	boolean isGameLost(final FixtureTeamResult fixtureTeamResult) {
		return result(fixtureTeamResult) > 0;
	}

	boolean isGameWon(final FixtureTeamResult fixtureTeamResult) {
		return result(fixtureTeamResult) < 0;
	}

	private int result(final FixtureTeamResult fixtureTeamResult) {
		return fixtureTeamResult.getGoalsLost().compareTo(fixtureTeamResult.getGoalsShot());
	}

}
