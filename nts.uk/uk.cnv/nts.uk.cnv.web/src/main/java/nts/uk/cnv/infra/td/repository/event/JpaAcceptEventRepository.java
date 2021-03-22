package nts.uk.cnv.infra.td.repository.event;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.event.AcceptEvent;
import nts.uk.cnv.dom.td.event.AcceptEventRepository;
import nts.uk.cnv.infra.td.entity.event.NemTdAcceptEvent;

public class JpaAcceptEventRepository extends JpaRepository implements AcceptEventRepository {

	@Override
	public Optional<String> getNewestAcceptId() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void regist(AcceptEvent acceptEvent) {
		this.commandProxy().insert(NemTdAcceptEvent.toEntity(acceptEvent));

	}


}
