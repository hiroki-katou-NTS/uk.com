package nts.uk.cnv.dom.td.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.cnv.dom.td.alteration.summary.AlterationStatusPolicy;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.dom.td.event.accept.AcceptEvent;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEvent;
import nts.uk.cnv.dom.td.event.order.OrderEvent;

public class EventPolicy {
	
	// 進行先の開発状況
	private final DevelopmentStatus destinationStatus;
	
	public EventPolicy(DevelopmentStatus destinationStatus) {
		// 先頭の開発状況はイベントに紐付かないため
		if(destinationStatus.isFirst()) {
			throw new BusinessException(new RawErrorMessage(destinationStatus.strStatus + "のイベントは発行できません。"));
		}
		this.destinationStatus = destinationStatus;
	}

	/**
	 * イベントを発行する上で守らなければならない制約チェックする
	 * @param require
	 * @param alterations
	 * @return
	 */
	public List<AlterationSummary> check(Require require, List<AlterationSummary> targetAlters) {
		
		// 対象のorutaがすでにイベントに紐付けられていないかチェックする
		List<EventId> alreadyEventIds = getEventIds(require, targetAlters);
		if(alreadyEventIds.size() > 0) {
			throw new BusinessException(new RawErrorMessage(destinationStatus.strStatus + "です。"));
		}
		
		// orutaの進捗進行時の制約をチェックする
		return new AlterationStatusPolicy(destinationStatus)
			.checkError(require, targetAlters);
	}
	
	private List<EventId> getEventIds(Require require, List<AlterationSummary> targetAlters) {
		
		Map<DevelopmentStatus, Function<List<String>, List<? extends Event>>> MAP = new HashMap<>();
		MAP.put(DevelopmentStatus.ORDERED, alterIds -> require.getOrderEventByAlter(alterIds));
		MAP.put(DevelopmentStatus.DELIVERED, alterIds -> require.getDeliveryEventByAlter(alterIds));
		MAP.put(DevelopmentStatus.ACCEPTED, alterIds -> require.getAcceptEventByAlter(alterIds));
		
		List<String> alterIds = targetAlters.stream()
				.map(ta -> ta.getAlterId()).collect(Collectors.toList());
		List<? extends Event> alreadyEvents = MAP.get(destinationStatus).apply(alterIds);
		
		return alreadyEvents.stream()
				.map(ae -> ae.getEventId())
				.collect(Collectors.toList());
	}
	
	
	
	public interface Require extends AlterationStatusPolicy.Require{
		List<OrderEvent> getOrderEventByAlter(List<String> alters);
		List<DeliveryEvent> getDeliveryEventByAlter(List<String> alters);
		List<AcceptEvent> getAcceptEventByAlter(List<String> alters);
	}
}
