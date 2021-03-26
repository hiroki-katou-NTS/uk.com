package nts.uk.cnv.dom.td.event.delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryEventRepository {
	Optional<String> getNewestDeliveryId();

	void regist(DeliveryEvent deliveryEvent);

	Optional<String> getEventName(String eventId);

	List<DeliveryEvent> getList();
}
