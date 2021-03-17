package nts.uk.cnv.dom.td.event;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class OrderEvent implements Comparable<OrderEvent> {
	private EventId eventId;
	private GeneralDateTime datetime;
	private EventMetaData meta;
	private List<String> alterationIds;

	public static OrderEvent create(ProvideOrderIdRequire require, EventMetaData meta, List<String> alterationIds) {
		EventId id = EventIdProvider.provideOrderId(require);
		return new OrderEvent(
				id,
				GeneralDateTime.now(),
				new EventMetaData(
					meta.name,
					meta.userName),
				alterationIds);
	}

	@RequiredArgsConstructor
	private class RequireImpl implements EventIdProvider.ProvideOrderIdRequire{
		private final OrderEventRepository repository;

		@Override
		public Optional<String> getNewestOrderId() {
			return repository.getNewestOrderId();
		}
	}

	@Override
	public int compareTo(OrderEvent o) {
		return this.datetime.compareTo(o.datetime);
	}
}
