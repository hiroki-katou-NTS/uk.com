package nts.uk.cnv.app.td.query.feature;

import java.util.List;

import javax.inject.Inject;

import lombok.val;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.feature.GetFeatureAlter;

public class GetAlterNotOrderedQuery {
	
	@Inject
	AlterationSummaryRepository alterationSummaryRepo;
	
	public List<AlterationSummary> get(String featureId) {
		val require = new RequireImpl();
		return GetFeatureAlter.getOfNotOrdered(require, featureId);
	}
	
	private class RequireImpl implements GetFeatureAlter.Require {

		@Override
		public List<AlterationSummary> getOfNotOrdered(String featureId, DevelopmentProgress devProgress) {
			return alterationSummaryRepo.getByFeature(featureId, devProgress);
		}
	}
}
