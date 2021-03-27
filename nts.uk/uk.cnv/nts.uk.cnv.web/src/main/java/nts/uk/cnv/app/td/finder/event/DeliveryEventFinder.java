package nts.uk.cnv.app.td.finder.event;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEvent;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEventRepository;

@Stateless
public class DeliveryEventFinder {

	@Inject
	private AlterationSummaryRepository alterationSummaryRepository;

	@Inject
	private DeliveryEventRepository deliveryEventRepo;

	public List<AlterationSummary> getBy(String deliveryId) {
		return alterationSummaryRepository.getByEvent(deliveryId, DevelopmentProgress.deliveled());
	}

	public List<DeliveryEvent> getList() {
		return deliveryEventRepo.getList();
	}
}
