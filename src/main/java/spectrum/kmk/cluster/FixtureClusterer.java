package spectrum.kmk.cluster;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.clustering.MultiKMeansPlusPlusClusterer;
import org.springframework.stereotype.Component;

import spectrum.kmk.liga.Fixture;
import spectrum.kmk.service.Clusterer;

@Component
class FixtureClusterer implements Clusterer<Fixture> {

	private static final int CLUSTER_COUNT = 4;

	@Override
	public void clusterize(final Collection<Fixture> fixtures) {
		final KMeansPlusPlusClusterer<NormalizedClusterable<FixtureClusterDto>> clusterer = new KMeansPlusPlusClusterer<>(
				CLUSTER_COUNT, 10000);

		final List<FixtureClusterDto> clusterInput = fixtures.stream().map(FixtureClusterDto::new).//
				collect(Collectors.toList());

		final List<NormalizedClusterable<FixtureClusterDto>> normalizeClusterableList = normalizeClusterable(
				clusterInput);

		final MultiKMeansPlusPlusClusterer<NormalizedClusterable<FixtureClusterDto>> multiClusterer = new MultiKMeansPlusPlusClusterer<>(
				clusterer, 300);

		final List<CentroidCluster<NormalizedClusterable<FixtureClusterDto>>> clusterResults = multiClusterer
				.cluster(normalizeClusterableList);
		//

		for (int i = 0; i < clusterResults.size(); i++) {

			final CentroidCluster<NormalizedClusterable<FixtureClusterDto>> cluster = clusterResults.get(i);
			System.out.println(">>>>>> Cluster (" + Arrays.toString(cluster.getCenter().getPoint()) + ") ["
					+ cluster.getPoints().size() + "]" + i);

			for (final NormalizedClusterable<FixtureClusterDto> locationWrapper : cluster.getPoints()) {
				final Fixture fixture = locationWrapper.getBussinesClusterable().getFixture();
				final String homeTeamName = fixture.getHome().getName();
				final String awayTeamName = fixture.getAway().getName();

				System.out.println(homeTeamName + " - " + awayTeamName + //
						" [" + fixture.getGoalsHome() + ":" + fixture.getGoalsAway() + "]");
			}
		}

	}

	<T extends Clusterable> List<NormalizedClusterable<T>> normalizeClusterable(final Collection<T> input) {

		final Integer dimencionsCount = input.stream().findAny().map((c) -> c.getPoint().length).//
				orElse(0);
		final List<Normalizer<Double>> normalizerList = createNormaliserList(input, dimencionsCount);

		final List<NormalizedClusterable<T>> normalizedClusterableList = new ArrayList<>();
		for (final T clusterable : input) {
			final Clusterable normalized = normalizeDimension(clusterable, dimencionsCount - 1, normalizerList);
			normalizedClusterableList.add(new NormalizedClusterable<>(clusterable, normalized));
		}
		return Collections.unmodifiableList(normalizedClusterableList);
	}

	Clusterable normalizeDimension(final Clusterable clusterable, final int dimension,
			final List<Normalizer<Double>> normalizerList) {
		if (dimension < 0) {
			return clusterable;
		}

		final double[] points = clusterable.getPoint();
		final double valueForDim = points[dimension];
		final Normalizer<Double> normalizer = normalizerList.get(dimension);
		final BigDecimal normalizedValue = normalizer.apply(valueForDim);
		final double[] copy = Arrays.copyOf(points, points.length);
		copy[dimension] = normalizedValue.doubleValue();
		final Clusterable normalized = new DoublePoint(copy);
		return normalizeDimension(normalized, dimension - 1, normalizerList);
	}

	private List<Normalizer<Double>> createNormaliserList(final Collection<? extends Clusterable> input,
			final Integer dimencionsCount) {
		final List<Normalizer<Double>> normalizerList = new ArrayList<>();
		// IntStream.range(0, dimencionsCount).map((dim)->{
		// final Double min = getMinForDimension(dim, input.stream());
		// final Double max = getMaxForDimension(dim, input.stream());
		// final Normalizer<Double> normalizer = new Normalizer<>(min, max, 1d);
		// return normalizer;
		// })
		for (int i = 0; i < dimencionsCount; i++) {

			final Double min = getMinForDimension(i, input.stream());
			final Double max = getMaxForDimension(i, input.stream());
			final Normalizer<Double> normalizer = new Normalizer<>(min, max, 1d);
			normalizerList.add(normalizer);

		}
		return normalizerList;
	}

	private Double getMaxForDimension(final int i, final Stream<? extends Clusterable> stream) {
		return stream.mapToDouble(c -> c.getPoint()[i]).max().orElse(0d);
	}

	private Double getMinForDimension(final int i, final Stream<? extends Clusterable> stream) {
		return stream.mapToDouble(c -> c.getPoint()[i]).min().orElse(0d);
	}

	static class NormalizedClusterable<T extends Clusterable> implements Clusterable {

		private final T bussinesClusterable;
		private final Clusterable noramlizedClusterable;

		NormalizedClusterable(final T bussinesClusterable, final Clusterable noramlizedClusterable) {
			this.bussinesClusterable = bussinesClusterable;
			this.noramlizedClusterable = noramlizedClusterable;
		}

		public T getBussinesClusterable() {
			return bussinesClusterable;
		}

		@Override
		public double[] getPoint() {
			return noramlizedClusterable.getPoint();
		}

	}

	static class Normalizer<T extends Number> implements Function<T, BigDecimal> {

		private final T min;
		private final T max;
		private final T scale;

		Normalizer(final T min, final T max, final T scale) {
			this.min = min;
			this.max = max;
			this.scale = scale;
		}

		@Override
		public BigDecimal apply(final T value) {

			final double v = value.doubleValue();
			final double d = max.doubleValue() != min.doubleValue() ? max.doubleValue() - min.doubleValue() : 1;

			final BigDecimal newValue = BigDecimal.valueOf(scale.doubleValue() * (v - min.doubleValue()) / d);

			return newValue;
		}

	}

}