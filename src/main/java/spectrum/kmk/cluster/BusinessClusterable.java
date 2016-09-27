package spectrum.kmk.cluster;

import org.apache.commons.math3.ml.clustering.Clusterable;

public interface BusinessClusterable<T extends Clusterable> extends Clusterable {

	T getBussinesClusterable();

}