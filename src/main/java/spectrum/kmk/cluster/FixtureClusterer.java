package spectrum.kmk.cluster;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.clustering.MultiKMeansPlusPlusClusterer;
import org.springframework.stereotype.Component;

import spectrum.kmk.liga.Fixture;
import spectrum.kmk.service.Clusterer;

@Component
class FixtureClusterer implements Clusterer<Fixture, FixtureClusterable> {

	private static final int CLUSTER_COUNT = 4;

	private final NormalizerFunctions normalizerFunctions = new NormalizerFunctions();

	@Override
	public List<CentroidCluster<BusinessClusterable<FixtureClusterable>>> clusterize(
			final Collection<Fixture> fixtures) {
		final KMeansPlusPlusClusterer<BusinessClusterable<FixtureClusterable>> clusterer = new KMeansPlusPlusClusterer<>(
				CLUSTER_COUNT, 10000);

		final List<FixtureClusterable> clusterInput = fixtures.stream().map(FixtureClusterable::new).//
				collect(Collectors.toList());

		final List<BusinessClusterable<FixtureClusterable>> normalizeClusterableList = normalizerFunctions
				.normalizeClusterable(clusterInput);

		final MultiKMeansPlusPlusClusterer<BusinessClusterable<FixtureClusterable>> multiClusterer = new MultiKMeansPlusPlusClusterer<>(
				clusterer, 300);

		final List<CentroidCluster<BusinessClusterable<FixtureClusterable>>> clusterResults = multiClusterer
				.cluster(normalizeClusterableList);
		return Collections.unmodifiableList(clusterResults);
	}

}