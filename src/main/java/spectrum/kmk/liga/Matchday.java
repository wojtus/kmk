package spectrum.kmk.liga;

import java.util.List;

class Matchday {

	private final List<Fixture> fixtures;

	Matchday(final List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}

	public List<Fixture> getFixtures() {
		return fixtures;
	}
}
