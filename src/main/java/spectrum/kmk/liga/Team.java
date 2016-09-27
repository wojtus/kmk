package spectrum.kmk.liga;

public class Team {

	private final String name;
	private final Long value;

	public Team(final String name, final Long value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Long getValue() {
		return value;
	}

}
