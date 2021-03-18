package nts.uk.cnv.app.td.finder.event;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

@Stateless
public class DeliveryEventFinder {
	
	@Inject
	private AlterationSummaryRepository alterationSummaryRepository;
	
	public List<AlterationSummary> getBy(String deliveryId) {
		return alterationSummaryRepository.getByEvent(deliveryId, DevelopmentStatus.DELIVERED);
	}
}
