package nts.uk.cnv.dom.td.event;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.event.EventIdProvider.ProvideOrderIdRequire;

/**
 * 発注イベント
 * @author ai_muto
 *
 */
@Getter
public class OrderEvent implements Comparable<OrderEvent> {
	private EventId eventId;
	private GeneralDateTime datetime;
	private EventMetaData meta = new EventMetaData();
	private List<String> alterationIds;


	private OrderEvent(EventId eventId, String name, String userName, List<String> alterationIds) {
		this.eventId = eventId;
		this.datetime = GeneralDateTime.now();
		this.meta.name = name;
		this.meta.userName = userName;
		this.alterationIds = alterationIds;
	}

	public static OrderEvent create(ProvideOrderIdRequire require, EventMetaData meta, List<String> alterationIds) {
		EventId id = EventIdProvider.provideOrderId(require);
		return new OrderEvent(id, meta.name, meta.userName, alterationIds);
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
