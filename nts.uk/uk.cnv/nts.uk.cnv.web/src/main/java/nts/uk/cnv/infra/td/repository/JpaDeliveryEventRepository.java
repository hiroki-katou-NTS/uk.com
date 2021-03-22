package nts.uk.cnv.infra.td.repository;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.event.DeliveryEvent;
import nts.uk.cnv.dom.td.event.DeliveryEventRepository;
import nts.uk.cnv.infra.td.entity.event.NemTdDeliveryEvent;

public class JpaDeliveryEventRepository extends JpaRepository implements DeliveryEventRepository {
	private static final String SELECT_NEWEST_QUERY = ""
			+ "SELET de.eventId FROM NemTdDeliveryEvent de"
			+ " GROPU BY de.eventId, de.datetime"
			+ " HAVING MAX(de.datetime) = de.datetime";

	@Override
	public Optional<String> getNewestDeliveryId() {
		return this.queryProxy()
				.query(SELECT_NEWEST_QUERY, String.class)
				.getSingle();
	}

	@Override
	public void regist(DeliveryEvent deliveryEvent) {
		this.commandProxy().insert(NemTdDeliveryEvent.toEntity(deliveryEvent));
	}


}
