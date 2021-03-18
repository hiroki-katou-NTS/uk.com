package nts.uk.cnv.dom.td.feature;

import lombok.val;
import nts.arc.task.tran.AtomTask;

/**
 * Featureを登録する
 */
public class FeatureService {
	
	public AtomTask createFeature(Require require, String featureName, String description) {
		val feature = Feature.newFeature(featureName, description);
		return AtomTask.of(() -> {
			require.regist(feature);
		});
	}
	
	public interface Require {
		void regist(Feature feature);
	}
}
