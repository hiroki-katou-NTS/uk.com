package nts.uk.cnv.dom.td.event;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDateTime;

/**
 * 発注イベント
 * @author ai_muto
 *
 */
@Getter
@Stateless
public class OrderEvent implements Comparable<OrderEvent> {
	private EventId eventId;
	private GeneralDateTime datetime;
	private String name;
	private String userName;
	private List<String> alterationIds;

	@Inject
	private OrderEventRepository orderEventRepo;

	private OrderEvent(EventId eventId, String name, String userName, List<String> alterationIds) {
		this.eventId = eventId;
		this.datetime = GeneralDateTime.now();
		this.name = name;
		this.userName = userName;
		this.alterationIds = alterationIds;
	}

	public OrderEvent create(String name, String userName, List<String> alterationIds) {
		RequireImpl require = new RequireImpl(orderEventRepo);
		EventId id = EventIdProvider.provideOrderId(require);
		return new OrderEvent(id, name, userName, alterationIds);
	}

	@RequiredArgsConstructor
	private class RequireImpl implements EventIdProvider.ProvideOrderIdRequire{
		private final OrderEventRepository repository;

		@Override
		public String getNewestOrderId() {
			return repository.getNewestOrderId();
		}
	}

	@Override
	public int compareTo(OrderEvent o) {
		return this.datetime.compareTo(o.datetime);
	}
}
