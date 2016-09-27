package spectrum.kmk.cluster;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.clustering.MultiKMeansPlusPlusClusterer;
import org.springframework.stereotype.Component;

import spectrum.kmk.liga.Team;

@Component
class TeamSMVClusterer implements Clusterer<Team, TeamClusterable> {

	private final NormalizerFunctions normalizerFunctions = new NormalizerFunctions();

	@Override
	public List<CentroidCluster<BusinessClusterable<TeamClusterable>>> clusterize(final Collection<Team> teams) {

		final List<TeamClusterable> teamClusterables = teams.stream().map(TeamClusterable::new)
				.collect(Collectors.toList());
		final List<BusinessClusterable<TeamClusterable>> normalizedClusterable = normalizerFunctions
				.normalizeClusterable(teamClusterables);
		final KMeansPlusPlusClusterer<BusinessClusterable<TeamClusterable>> clusterer = new KMeansPlusPlusClusterer<>(5,
				10000);
		final MultiKMeansPlusPlusClusterer<BusinessClusterable<TeamClusterable>> multiClusterer = new MultiKMeansPlusPlusClusterer<>(
				clusterer, 20);
		final List<CentroidCluster<BusinessClusterable<TeamClusterable>>> clusterList = multiClusterer
				.cluster(normalizedClusterable);
		return clusterList;

	}

}
