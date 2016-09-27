package spectrum.kmk.service;

import java.util.List;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;

import spectrum.kmk.cluster.BusinessClusterable;

public interface ClusterService<T extends Clusterable> {

	List<CentroidCluster<BusinessClusterable<T>>> clusterObjects();

}