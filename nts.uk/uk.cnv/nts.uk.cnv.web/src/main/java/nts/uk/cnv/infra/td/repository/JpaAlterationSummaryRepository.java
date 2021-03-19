package nts.uk.cnv.infra.td.repository;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.alteration.summary.DevelopmentState;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlterationView;

public class JpaAlterationSummaryRepository extends JpaRepository implements AlterationSummaryRepository {

	@Override
	public List<AlterationSummary> getByFeature(String featureId) {
		// TODO 自動生成されたメソッド・スタブ
		val result = new ArrayList<AlterationSummary>();
		result.add(new AlterationSummary("おるたID_1", GeneralDateTime.now(), "テーブルID_1", DevelopmentState.NOT_ORDERING, new AlterationMetaData("ゆーざ_1", "こめんと_1"), featureId ));
		result.add(new AlterationSummary("おるたID_2", GeneralDateTime.now(), "テーブルID_2", DevelopmentState.ORDERED, new AlterationMetaData("ゆーざ_2", "こめんと_2"), featureId ));
		result.add(new AlterationSummary("おるたID_3", GeneralDateTime.now(), "テーブルID_3", DevelopmentState.DELIVERED, new AlterationMetaData("ゆーざ_3", "こめんと_3"), featureId ));
		result.add(new AlterationSummary("おるたID_4", GeneralDateTime.now(), "テーブルID_4", DevelopmentState.ACCEPTED, new AlterationMetaData("ゆーざ_4", "こめんと_4"), featureId ));
		return result;
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
		String query = "select * from NemTdAlterationView vi where ";
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
