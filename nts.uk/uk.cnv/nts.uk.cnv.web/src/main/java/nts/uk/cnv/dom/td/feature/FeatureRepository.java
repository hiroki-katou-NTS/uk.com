package nts.uk.cnv.dom.td.feature;

import java.util.List;

public interface FeatureRepository {
	
	void insert(Feature domain);
	
	List<Feature> get();
	
}
