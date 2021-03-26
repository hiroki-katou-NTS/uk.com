package nts.uk.cnv.infra.td.repository.event;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEvent;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEventRepository;
import nts.uk.cnv.infra.td.entity.event.NemTdDeliveryEvent;

public class JpaDeliveryEventRepository extends JpaRepository implements DeliveryEventRepository {
	private static final String SELECT_NEWEST_QUERY = ""
			+ "SELECT de.eventId FROM NemTdDeliveryEvent de"
			+ " ORDER BY de.eventId DESC";

	private static final String SELECT_ALL = ""
			+ "SELECT de FROM NemTdDeliveryEvent de"
			+ " ORDER BY de.eventId DESC";

	@Override
	public Optional<String> getNewestDeliveryId() {
		return this.queryProxy()
				.query(SELECT_NEWEST_QUERY, String.class)
				.getList().stream()
				.findFirst();
	}

	private static final String SELECT_EVENTID = ""
			+ "SELECT de.eventId, de.name "
			+ "FROM NemTdDeliveryEvent de"
			+ "WHERE de.eventId = :eventId";

	@Override
	public Optional<String> getEventName(String eventId){
		return this.queryProxy()
				.query(SELECT_EVENTID, String.class)
				.setParameter("eventId", eventId)
				.getSingle();
	}

	@Override
	public void regist(DeliveryEvent deliveryEvent) {
		this.commandProxy().insert(NemTdDeliveryEvent.toEntity(deliveryEvent));
	}

	@Override
	public List<DeliveryEvent> getList() {
		return this.queryProxy()
				.query(SELECT_ALL, NemTdDeliveryEvent.class)
				.getList(entity -> entity.toDomain());
	}


}
