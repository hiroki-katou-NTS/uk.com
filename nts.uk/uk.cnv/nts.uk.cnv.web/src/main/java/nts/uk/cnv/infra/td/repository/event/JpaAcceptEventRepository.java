package nts.uk.cnv.infra.td.repository.event;

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


}
