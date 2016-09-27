package spectrum.kmk;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spectrum.kmk.cluster.FixtureClusterable;
import spectrum.kmk.cluster.TeamClusterable;
import spectrum.kmk.service.ClusterService;
import spectrum.kmk.service.DataImportService;

@SpringBootApplication
@RestController
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}

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

	@RequestMapping("/")
	String home() {
		return "Hello World";
	}

	@RequestMapping("/import")
	String importData() {
		return "Hello Docker World";
	}

}