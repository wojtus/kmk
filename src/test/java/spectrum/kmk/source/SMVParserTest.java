package spectrum.kmk.source;

import org.junit.Assert;
import org.junit.Test;

import spectrum.kmk.source.SMVParser;

public class SMVParserTest {
	// 617,100,000 €

	@Test
	public void testSMVParser() {
		final SMVParser parser = new SMVParser();
		final String string = " 617,100,000 € ";
		final long smv = parser.getSMV(string);
		Assert.assertEquals(617100000, smv);

	}
}
