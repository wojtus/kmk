package spectrum.kmk;

import java.util.List;
import java.util.concurrent.Executor;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spectrum.kmk.cluster.BusinessClusterable;
import spectrum.kmk.cluster.FixtureClusterable;
import spectrum.kmk.cluster.TeamClusterable;
import spectrum.kmk.service.ClusterService;
import spectrum.kmk.service.DataImportService;
import spectrum.kmk.source.DefaultLigaFactory;

@SpringBootApplication
@RestController
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ClusterService<TeamClusterable> teamClusterService;
	@Autowired
	private ClusterService<FixtureClusterable> fixtureClusterService;

	@Autowired
	private DataImportService importService;

	@Bean
	public Executor threadPoolTaskExecutor() {
		final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(6);
		return threadPoolTaskExecutor;
	}

	@RequestMapping("/clusterFixtures")
	String clusterFixtures() {
		final List<CentroidCluster<BusinessClusterable<FixtureClusterable>>> objects = fixtureClusterService
				.clusterObjects();
		return "clustered fixtures" + objects.size();
	}

	@RequestMapping("/clusterTeams")
	String clusterTeams() {
		final List<CentroidCluster<BusinessClusterable<TeamClusterable>>> objects = teamClusterService.clusterObjects();
		return "clustered teams" + objects.size();
	}

	@RequestMapping("/")
	String home() {
		return "Hello World";
	}

	@RequestMapping("/import")
	String importData() {
		try {
			importService.importData(DefaultLigaFactory.getTeams(), DefaultLigaFactory.getFixtures());
		} catch (final Exception e) {
			logger.error("Error importing data", e);
		}
		return "imported";
	}

}