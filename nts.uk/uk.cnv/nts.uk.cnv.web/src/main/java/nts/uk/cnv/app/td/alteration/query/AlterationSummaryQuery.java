package nts.uk.cnv.app.td.alteration.query;

import java.util.List;

import javax.inject.Inject;

import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

public class AlterationSummaryQuery {
	
	@Inject
	AlterationSummaryRepository alterationSummaryRepo;
	
	public List<AlterationSummary> getAll(String featureId) {
		return alterationSummaryRepo.getByFeature(featureId);
	}
	
	public List<AlterationSummary> getOfNotOrderedByFeature(String featureId) {
		return alterationSummaryRepo.getByFeature(featureId, DevelopmentStatus.NOT_ORDER);
	}
	
	public List<AlterationSummary> getOfOrderedByEvent(String eventId) {
		return alterationSummaryRepo.getByEvent(eventId, DevelopmentStatus.ORDERED);
	}
}
