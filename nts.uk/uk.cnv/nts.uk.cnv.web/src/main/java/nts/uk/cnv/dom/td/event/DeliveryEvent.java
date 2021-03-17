package nts.uk.cnv.dom.td.event;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.event.EventIdProvider.ProvideDeliveryIdRequire;

/**
 * 納品イベント
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public class DeliveryEvent implements Comparable<DeliveryEvent> {
	private EventId eventId;
	private GeneralDateTime datetime;
	private EventMetaData meta;
	private List<String> alterationIds;

	public static DeliveryEvent create(ProvideDeliveryIdRequire require, EventMetaData meta, List<String> alterationIds) {
		EventId id = EventIdProvider.provideDeliveryId(require);
		return new DeliveryEvent(
				id,
				GeneralDateTime.now(),
				meta,
				alterationIds);
	}

	@RequiredArgsConstructor
	private class RequireImpl implements EventIdProvider.ProvideDeliveryIdRequire{
		private final DeliveryEventRepository repository;

		@Override
		public Optional<String> getNewestDeliveryId() {
			return repository.getNewestDeliveryId();
		}
	}

	@Override
	public int compareTo(DeliveryEvent o) {
		return this.datetime.compareTo(o.datetime);
	}
}
