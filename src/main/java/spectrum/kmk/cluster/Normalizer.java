package spectrum.kmk.cluster;

import java.math.BigDecimal;
import java.util.function.Function;

class Normalizer<T extends Number> implements Function<T, BigDecimal> {

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