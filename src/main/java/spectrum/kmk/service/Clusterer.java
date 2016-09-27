package spectrum.kmk.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;

import spectrum.kmk.cluster.BusinessClusterable;

public interface Clusterer<INPUT, OUTPUT extends Clusterable> {
	List<CentroidCluster<BusinessClusterable<OUTPUT>>> clusterize(final Collection<INPUT> ligaDtos);
}