package nts.uk.cnv.app.td.finder.event;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.event.EventId;
import nts.uk.cnv.dom.td.event.order.OrderEvent;
import nts.uk.cnv.dom.td.event.order.OrderEventRepository;

@Stateless
public class OrderEventFinder {

	@Inject
	private AlterationSummaryRepository alterationSummaryRepository;

	@Inject
	private OrderEventRepository orderEventRepo;

	/**
	 *
	 * @param featureId
	 * @return
	 */
	public List<AlterationSummary> getOfNotOrdered(String featureId) {
		return alterationSummaryRepository.getByFeature(featureId, DevelopmentProgress.notOrdered());
	}

	public List<AlterationSummary> getBy(String orderId) {
		return alterationSummaryRepository.getByEvent(orderId, DevelopmentProgress.ordered());
	}

	public List<OrderEvent> getList() {
		return orderEventRepo.getList();
	}
	
	public List<OrderEvent> getByAlter(List<String> alters) {
		return orderEventRepo.getByAlter(alters);
	}
}
