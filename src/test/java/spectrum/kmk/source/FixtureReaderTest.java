package spectrum.kmk.source;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FixtureReaderTest {

	public static List<FixtureImportDto> getTestFixtures() {
		final FixtureReader teamReader = new FixtureReader();

		try (InputStream is = ClassLoader.getSystemResource("1bundesliga-fixtures.json").openStream()) {
			final List<FixtureImportDto> fixtures = teamReader.getFixtures(is);
			return fixtures;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void testGetFixtures() throws Exception {

		final List<FixtureImportDto> fixtures = getTestFixtures();
		Assert.assertNotNull(fixtures);
		Assert.assertEquals(306, fixtures.size());
	}
}
