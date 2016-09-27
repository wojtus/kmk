package spectrum.kmk.source;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FixtureReaderTest {

	@Test
	public void testGetFixtures() throws Exception {
		final LigaReader<FixtureImportDto> reader = new FixtureReader();
		final List<FixtureImportDto> fixtures = reader.readObjects();
		Assert.assertNotNull(fixtures);
		Assert.assertEquals(306, fixtures.size());
	}
}
