package spectrum.kmk.source;

import java.util.List;
import java.util.function.Supplier;

public class DefaultLigaFactory {

	public static Supplier<List<FixtureImportDto>> getFixtures() {
		return () -> {
			return new FixtureReader().readObjects();
		};
	}

	public static Supplier<List<TeamImportDto>> getTeams() {
		return () -> {
			return new TeamReader().readObjects();
		};
	}

}
