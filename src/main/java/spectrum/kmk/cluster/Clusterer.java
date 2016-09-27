package spectrum.kmk.cluster;

import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;

public interface Clusterer<INPUT, OUTPUT extends Clusterable> {
	List<CentroidCluster<BusinessClusterable<OUTPUT>>> clusterize(final Collection<INPUT> ligaDtos);
}