package nts.uk.cnv.infra.td.repository;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.event.OrderEvent;
import nts.uk.cnv.dom.td.event.OrderEventRepository;
import nts.uk.cnv.infra.td.entity.event.NemTdOrderEvent;

public class JpaOrderEventRepository extends JpaRepository implements OrderEventRepository {
	private static final String SELECT_NEWEST_QUERY = ""
			+ "SELECT oe.eventId FROM NemTdOrderEvent oe"
			+ " GROUP BY oe.eventId, oe.datetime"
			+ " HAVING MAX(oe.datetime) = oe.datetime";

	@Override
	public Optional<String> getNewestOrderId() {
		return this.queryProxy()
			.query(SELECT_NEWEST_QUERY, String.class)
			.getSingle();
	}

	@Override
	public void regist(OrderEvent orderEvent) {
		this.commandProxy().insert(NemTdOrderEvent.toEntity(orderEvent));
	}

}
