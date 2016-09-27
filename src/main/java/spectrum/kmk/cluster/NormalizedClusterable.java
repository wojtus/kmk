package spectrum.kmk.cluster;

import org.apache.commons.math3.ml.clustering.Clusterable;

class NormalizedClusterable<T extends Clusterable> implements Clusterable, BusinessClusterable<T> {

	private final T bussinesClusterable;
	private final Clusterable noramlizedClusterable;

	NormalizedClusterable(final T bussinesClusterable, final Clusterable noramlizedClusterable) {
		this.bussinesClusterable = bussinesClusterable;
		this.noramlizedClusterable = noramlizedClusterable;
	}

	@Override
	public T getBussinesClusterable() {
		return bussinesClusterable;
	}

	@Override
	public double[] getPoint() {
		return noramlizedClusterable.getPoint();
	}

}