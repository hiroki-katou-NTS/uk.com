package nts.uk.cnv.infra.td.repository.alteration;

import java.util.Arrays;
import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlterationView;

public class JpaAlterationSummaryRepository extends JpaRepository implements AlterationSummaryRepository {
	
	private String BaseSelect = " select v from NemTdAlterationView v";

	@Override
	public List<AlterationSummary> get(DevelopmentProgress devProgress) {
		String jpql = BaseSelect
					+ " where v." + NemTdAlterationView.jpqlWhere(devProgress);
		
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
				.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getByAlter(String alterId) {
		return this.getByAlter(Arrays.asList(alterId));
	}
	
	@Override
	public List<AlterationSummary> getByAlter(List<String> alterId) {
		String jpql = BaseSelect
					+ " where v.alterationId in :alterationId";
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
				.setParameter("alterationId", alterId)
				.getList(e -> e.toDomain());
	}
	
	@Override
	public List<AlterationSummary> getByFeature(String featureId) {
		String jpql = BaseSelect
					+ " where v.featureId=:featureId";
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("featureId", featureId)
			.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getByFeature(String featureId, DevelopmentStatus devStatus) {
		String jpql = BaseSelect
					+ " where v.featureId=:featureId"
					+ " and " + NemTdAlterationView.jpqlWhere(devStatus, "v");
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("featureId", featureId)
			.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress) {
		String jpql = BaseSelect
					+ " where v.featureId=:featureId"
					+ " and v." + NemTdAlterationView.jpqlWhere(devProgress);
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("featureId", featureId)
			.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress) {
		String jpql = BaseSelect
					+ " where v.tableId=:tableId"
					+ " and v." + NemTdAlterationView.jpqlWhere(devProgress);
		
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("tableId", tableId)
			.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getOlder(AlterationSummary alter, DevelopmentProgress devProgress) {
		String jpql = BaseSelect
				+ " where v.featureId=:featureId"
				+ " and v.time<:time"
				+ " and v." + NemTdAlterationView.jpqlWhere(devProgress);
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("featureId", alter.getFeatureId())
			.setParameter("time", alter.getTime())
			.getList(e -> e.toDomain());
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
