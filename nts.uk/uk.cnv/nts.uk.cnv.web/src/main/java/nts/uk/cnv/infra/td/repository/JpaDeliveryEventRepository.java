package nts.uk.cnv.infra.td.repository;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.event.DeliveryEvent;
import nts.uk.cnv.dom.td.event.DeliveryEventRepository;

public class JpaDeliveryEventRepository extends JpaRepository implements DeliveryEventRepository {

	@Override
	public Optional<String> getNewestDeliveryId() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void regist(DeliveryEvent orderEvent) {
		// TODO 自動生成されたメソッド・スタブ

	}


}
