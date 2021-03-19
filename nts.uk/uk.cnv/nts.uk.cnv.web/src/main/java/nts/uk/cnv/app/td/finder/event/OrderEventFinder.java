package nts.uk.cnv.app.td.finder.event;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

@Stateless
public class OrderEventFinder {
	
	@Inject
	private AlterationSummaryRepository alterationSummaryRepository;
	
	/**
	 * 
	 * @param featureId
	 * @return
	 */
	public List<AlterationSummary> getOfNotOrdered(String featureId) {
		return alterationSummaryRepository.getByFeature(featureId, DevelopmentProgress.notOrdered());
	}
	
	public List<AlterationSummary> getBy(String orderId) {
		return alterationSummaryRepository.getByEvent(orderId, DevelopmentStatus.ORDERED);
	}
}
