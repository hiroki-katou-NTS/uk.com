package nts.uk.cnv.dom.td.event.delivery;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.event.Event;
import nts.uk.cnv.dom.td.event.EventDetail;
import nts.uk.cnv.dom.td.event.EventId;
import nts.uk.cnv.dom.td.event.EventIdProvider;
import nts.uk.cnv.dom.td.event.EventIdProvider.ProvideDeliveryIdRequire;

/**
 * 納品イベント
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public class DeliveryEvent implements Event{
	private EventId eventId;
	private EventDetail detail;

	public static DeliveryEvent create(ProvideDeliveryIdRequire require, String eventName, String userName, List<String> alterationIds) {
		EventId id = EventIdProvider.provideDeliveryId(require);
		return new DeliveryEvent(
				id,
				new EventDetail(
						eventName,
						GeneralDateTime.now(),
						userName,
						alterationIds));
	}

	@RequiredArgsConstructor
	private class RequireImpl implements EventIdProvider.ProvideDeliveryIdRequire{
		private final DeliveryEventRepository repository;

		@Override
		public Optional<String> getNewestDeliveryId() {
			return repository.getNewestDeliveryId();
		}
	}
}
