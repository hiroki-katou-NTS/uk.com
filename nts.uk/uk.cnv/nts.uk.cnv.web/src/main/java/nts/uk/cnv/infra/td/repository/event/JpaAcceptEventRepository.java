package nts.uk.cnv.infra.td.repository.event;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.event.accept.AcceptEvent;
import nts.uk.cnv.dom.td.event.accept.AcceptEventRepository;
import nts.uk.cnv.infra.td.entity.event.NemTdAcceptEvent;

public class JpaAcceptEventRepository extends JpaRepository implements AcceptEventRepository {

	private static final String SELECT_NEWEST_QUERY = ""
			+ "SELECT ae.eventId FROM NemTdAcceptEvent ae"
			+ " ORDER BY ae.eventId DESC";

	@Override
	public Optional<String> getNewestAcceptId() {
		return this.queryProxy()
				.query(SELECT_NEWEST_QUERY, String.class)
				.getList().stream()
				.findFirst();
	}

	@Override
	public void regist(AcceptEvent acceptEvent) {
		this.commandProxy().insert(NemTdAcceptEvent.toEntity(acceptEvent));

	}

	@Override
	public List<AcceptEvent> getByAlter(List<String> alters) {
		String sql = "select oe from NemTdAcceptEvent ae"
				+ " join NemTdAcceptEventAltaration aea on ae.eventId = aea.pk.eventId"
				+ " where aea.pk.alterationId in :alters";
		return this.queryProxy().query(sql, NemTdAcceptEvent.class)
				.setParameter("alters", alters)
				.getList(entity -> entity.toDomain());
	}

}
