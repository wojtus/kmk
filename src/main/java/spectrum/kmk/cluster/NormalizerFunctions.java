package spectrum.kmk.cluster;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.DoublePoint;

class NormalizerFunctions {
	List<Normalizer<Double>> createNormaliserList(final Collection<? extends Clusterable> input,
			final Integer dimencionsCount) {
		final List<Normalizer<Double>> normalizerList = new ArrayList<>();

		for (int i = 0; i < dimencionsCount; i++) {

			final Double min = getMinForDimension(i, input.stream());
			final Double max = getMaxForDimension(i, input.stream());
			final Normalizer<Double> normalizer = new Normalizer<>(min, max, 1d);
			normalizerList.add(normalizer);

		}
		return normalizerList;
	}

	<T extends Clusterable> List<BusinessClusterable<T>> normalizeClusterable(final Collection<T> input) {

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

	private Double getMaxForDimension(final int i, final Stream<? extends Clusterable> stream) {
		return stream.mapToDouble(c -> c.getPoint()[i]).max().orElse(0d);
	}

	private Double getMinForDimension(final int i, final Stream<? extends Clusterable> stream) {
		return stream.mapToDouble(c -> c.getPoint()[i]).min().orElse(0d);
	}
}
