package nts.uk.cnv.infra.td.repository.event;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.event.order.OrderEvent;
import nts.uk.cnv.dom.td.event.order.OrderEventRepository;
import nts.uk.cnv.infra.td.entity.event.NemTdOrderEvent;

public class JpaOrderEventRepository extends JpaRepository implements OrderEventRepository {
	private static final String SELECT_NEWEST_QUERY = ""
			+ "SELECT oe.eventId FROM NemTdOrderEvent oe"
			+ " ORDER BY oe.eventId DESC";

	private static final String SELECT_ALL = ""
			+ "SELECT oe FROM NemTdOrderEvent oe"
			+ " ORDER BY oe.eventId DESC";

	@Override
	public Optional<String> getNewestOrderId() {
		return this.queryProxy()
			.query(SELECT_NEWEST_QUERY, String.class)
			.getList().stream()
			.findFirst();
	}

	@Override
	public void regist(OrderEvent orderEvent) {
		this.commandProxy().insert(NemTdOrderEvent.toEntity(orderEvent));
	}

	@Override
	public List<OrderEvent> getList() {
		return this.queryProxy()
				.query(SELECT_ALL, NemTdOrderEvent.class)
				.getList(entity -> entity.toDomain());
	}

	@Override
	public List<OrderEvent> getByAlter(List<String> alters) {
		String sql = "select oe from NemTdOrderEvent oe"
				+ " join NemTdOrderEventAltaration oea on oe.eventId = oea.pk.eventId"
				+ " where oea.pk.alterationId in :alters";
		return this.queryProxy().query(sql, NemTdOrderEvent.class)
				.setParameter("alters", alters)
				.getList(entity -> entity.toDomain());
	}

}
