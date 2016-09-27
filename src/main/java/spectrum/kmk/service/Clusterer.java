package spectrum.kmk.service;

import java.util.Collection;

public interface Clusterer<T> {
	void clusterize(final Collection<T> ligaDtos);
}