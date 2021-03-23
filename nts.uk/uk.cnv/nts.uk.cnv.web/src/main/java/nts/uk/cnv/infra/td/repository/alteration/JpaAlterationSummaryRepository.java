package nts.uk.cnv.infra.td.repository.alteration;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlterationView;

public class JpaAlterationSummaryRepository extends JpaRepository implements AlterationSummaryRepository {

	@Override
	public List<AlterationSummary> get(DevelopmentProgress devProgress) {
		
		String jpql = "select v from NemTdAlterationView v"
				+ " where v." + NemTdAlterationView.jpqlWhere(devProgress);
		
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
				.getList(e -> e.toDomain());
	}
	
	@Override
	public List<AlterationSummary> getByFeature(String featureId) {
		String sql = ""
				+ "SELECT v FROM NemTdAlterationView v"
				+ " WHERE v.featureId=:featureId";
		return this.queryProxy().query(sql, NemTdAlterationView.class)
			.setParameter("featureId", featureId)
			.getList(entity -> entity.toDomain());
	}

	@Override
	public List<AlterationSummary> getByFeature(String featureId, DevelopmentStatus devStatus) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress) {
		//TODO:仮実装
		String sql = ""
				+ "SELECT v FROM NemTdAlterationView v"
				+ " WHERE v.featureId=:featureId";
		return this.queryProxy().query(sql, NemTdAlterationView.class)
			.setParameter("featureId", featureId)
			.getList(entity -> entity.toDomain());
	}

	@Override
	public List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress) {
		//TODO:仮実装
		String sql = ""
				+ "SELECT v FROM NemTdAlterationView v"
				+ " WHERE v.tableId=:tableId";
		return this.queryProxy().query(sql, NemTdAlterationView.class)
			.setParameter("tableId", tableId)
			.getList(entity -> entity.toDomain());
	}

	@Override
	public List<AlterationSummary> getByEvent(String eventId, DevelopmentStatus devStatus) {
		String query = "select vi from NemTdAlterationView vi where ";
		switch(devStatus) {
		case ORDERED:
			query += "vi.orderedEventId";
			break;
		default:
				throw new RuntimeException("未対応です。実装してください。");
		}
		query += "= :eventId ";
		query += "order by vi.time asc";

		return this.queryProxy().query(query, NemTdAlterationView.class)
				.setParameter("eventId", eventId)
				.getList(entity -> entity.toDomain());
	}

}
