package nts.uk.cnv.dom.td.event.order;

import java.util.List;
import java.util.Optional;

public interface OrderEventRepository {

	void regist(OrderEvent orderEvent);
	
	Optional<String> getNewestOrderId();

	List<OrderEvent> getList();

	List<OrderEvent> getByAlter(List<String> alterations);
}
