package nts.uk.cnv.dom.td.event.delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryEventRepository {

	void regist(DeliveryEvent deliveryEvent);

	Optional<String> getEventName(String eventId);
	
	Optional<String> getNewestDeliveryId();

	List<DeliveryEvent> getList();

	List<DeliveryEvent> getByAlter(List<String> alterations);
}
