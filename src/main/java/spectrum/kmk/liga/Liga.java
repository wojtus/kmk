package spectrum.kmk.liga;

import java.util.List;

class Liga {

	private final List<Matchday> matchdays;

	Liga(final List<Matchday> matchdays) {
		this.matchdays = matchdays;
	}

	public List<Matchday> getMatchdays() {
		return matchdays;
	}

}
