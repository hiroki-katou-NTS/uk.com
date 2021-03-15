package nts.uk.cnv.dom.td.event;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDateTime;

/**
 * 納品イベント
 * @author ai_muto
 *
 */
@Getter
@Stateless
public class DeliveryEvent {
	private EventId eventId;
	private GeneralDateTime datetime;
	private String name;
	private String userName;
	private List<String> alterationIds;

	@Inject
	private DeliveryEventRepository deliveryEventRepo;

	private DeliveryEvent(EventId eventId, String name, String userName, List<String> alterationIds) {
		this.eventId = eventId;
		this.datetime = GeneralDateTime.now();
		this.name = name;
		this.alterationIds = alterationIds;
	}

	public DeliveryEvent create(String name, String userName, List<String> alterationIds) {
		RequireImpl require = new RequireImpl(deliveryEventRepo);
		EventId id = EventIdProvider.provideDeliveryId(require);
		return new DeliveryEvent(id, name, userName, alterationIds);
	}

	@RequiredArgsConstructor
	private class RequireImpl implements EventIdProvider.ProvideDeliveryIdRequire{
		private final DeliveryEventRepository repository;

		@Override
		public String getNewestDeliveryId() {
			return repository.getNewestDeliveryId();
		}
	}
}
