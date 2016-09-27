package spectrum.kmk.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spectrum.kmk.Application;
import spectrum.kmk.source.DefaultLigaFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
public class TeamClusterServiceTest {

	@Autowired
	private TeamClusterService clusterService;
	@Autowired
	private DataImportService importService;

	@Test
	public void testClusterTeams() throws Exception {
		importService.importData(DefaultLigaFactory.getTeams(), DefaultLigaFactory.getFixtures());
		clusterService.clusterObjects();
	}

}
