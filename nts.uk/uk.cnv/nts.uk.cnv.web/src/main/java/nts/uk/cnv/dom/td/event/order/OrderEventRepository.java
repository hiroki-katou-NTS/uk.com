package nts.uk.cnv.dom.td.event.order;

import java.util.List;
import java.util.Optional;

public interface OrderEventRepository {
	Optional<String> getNewestOrderId();

	void regist(OrderEvent orderEvent);

	List<OrderEvent> getList();

}
