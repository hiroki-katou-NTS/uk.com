package nts.uk.cnv.infra.td.repository;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.event.AcceptEvent;
import nts.uk.cnv.dom.td.event.AcceptEventRepository;

public class JpaAcceptEventRepository extends JpaRepository implements AcceptEventRepository {

	@Override
	public Optional<String> getNewestAcceptId() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void regist(AcceptEvent orderEvent) {
		// TODO 自動生成されたメソッド・スタブ

	}


}
