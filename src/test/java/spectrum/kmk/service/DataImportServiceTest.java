package spectrum.kmk.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spectrum.kmk.Application;
import spectrum.kmk.persistence.FixtureRepository;
import spectrum.kmk.persistence.TeamBo;
import spectrum.kmk.persistence.TeamRepository;
import spectrum.kmk.source.FixtureReaderTest;
import spectrum.kmk.source.TeamReaderTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
public class DataImportServiceTest {

	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private FixtureRepository fixtureRepository;
	@Autowired
	private DataImportService importService;

	@Test
	public void testImportData() throws Exception {

		importService.importData(TeamReaderTest::getTestTeams, FixtureReaderTest::getTestFixtures);
		assertEquals(18, teamRepository.count());
		assertEquals(306, fixtureRepository.count());

		final Iterable<TeamBo> findAll = teamRepository.findAll();
		findAll.forEach(t -> {
			Assert.assertTrue(t.getName().trim().length() > 3);
		});

		fixtureRepository.findAll().forEach(f -> {

			assertNotNull(f.getHome());
			assertNotNull(f.getAway());
			assertNotNull(f.getGoalsHome());
			assertNotNull(f.getGoalsAway());

		});

	}

}
