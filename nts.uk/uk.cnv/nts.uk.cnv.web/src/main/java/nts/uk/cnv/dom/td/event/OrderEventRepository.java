package nts.uk.cnv.dom.td.event;

import java.util.Optional;

public interface OrderEventRepository {
	Optional<String> getNewestOrderId();

	void regist(OrderEvent orderEvent);

}
