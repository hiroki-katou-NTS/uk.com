package nts.uk.cnv.infra.td.repository;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlterationView;

public class JpaAlterationSummaryRepository extends JpaRepository implements AlterationSummaryRepository {

	@Override
	public List<AlterationSummary> getByFeature(String featureId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getByFeature(String featureId, DevelopmentStatus devStatus) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getByEvent(String eventId, DevelopmentStatus devStatus) {
		String query = "select vi from NemTdAlterationView vi where ";
		switch(devStatus) {
		case ORDERED:
			query += "vi.orderEventId";
			break;
		case DELIVERED:
			query += "vi.deliveredEventId";
			break;
		default:
				throw new RuntimeException("未対応です。実装してください。");
		}
		query +=  "= :eventId";
		return this.queryProxy().query(query, NemTdAlterationView.class)
				.setParameter("eventId", eventId)
				.getList(entity -> entity.toDomain());
	}

}
