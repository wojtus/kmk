package spectrum.kmk.liga;

public class Fixture {

	private final Team home;
	private final Team away;

	private final int goalsHome;
	private final int goalsAway;

	public Fixture(final Team home, final Team away, final int goalsHome, final int goalsAway) {
		this.home = home;
		this.away = away;
		this.goalsHome = goalsHome;
		this.goalsAway = goalsAway;
	}

	public Team getAway() {
		return away;
	}

	public int getGoalsAway() {
		return goalsAway;
	}

	public int getGoalsHome() {
		return goalsHome;
	}

	public Team getHome() {
		return home;
	}

}
