package spectrum.kmk.cluster;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.clustering.MultiKMeansPlusPlusClusterer;
import org.springframework.stereotype.Component;

import spectrum.kmk.liga.Team;
import spectrum.kmk.service.Clusterer;

@Component
class TeamSMVClusterer implements Clusterer<Team> {

	@Override
	public void clusterize(final Collection<Team> teams) {

		CompletableFuture.supplyAsync(() -> {
			return getClusterInput(teams);
		}).thenAccept(this::clusterizeAndLog).join();

	}

	private void clusterizeAndLog(final List<TeamCluster> clusterInput) {
		final KMeansPlusPlusClusterer<TeamCluster> clusterer = new KMeansPlusPlusClusterer<>(5, 10000);
		final MultiKMeansPlusPlusClusterer<TeamCluster> multiClusterer = new MultiKMeansPlusPlusClusterer<>(clusterer,
				20);
		multiClusterer.cluster(clusterInput).stream().//
				map(this::getAndLogPoints).//
				flatMap(List::stream).//
				forEach(this::logTeamCluster);
	}

	private List<TeamCluster> getAndLogPoints(final CentroidCluster<TeamCluster> cluster) {
		final String center = String.format("%.0f", cluster.getCenter().getPoint()[0]);
		System.out.println(">>>>>> Cluster " + center);
		return cluster.getPoints();

	}

	private List<TeamCluster> getClusterInput(final Collection<Team> teams) {
		final List<TeamCluster> clusterInput = teams.stream().map(TeamCluster::new).//
				collect(Collectors.toList());
		return clusterInput;
	}

	private void logTeamCluster(final TeamCluster locationWrapper) {
		System.out.println(locationWrapper.getTeam().getName() + " - " + locationWrapper.getTeam().getValue());
	}
}
