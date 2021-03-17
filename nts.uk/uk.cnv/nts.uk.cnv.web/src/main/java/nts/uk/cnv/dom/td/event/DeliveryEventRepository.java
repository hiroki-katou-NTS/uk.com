package nts.uk.cnv.dom.td.event;

import java.util.Optional;

public interface DeliveryEventRepository {
	Optional<String> getNewestDeliveryId();

	void regist(DeliveryEvent deliveryEvent);
}
