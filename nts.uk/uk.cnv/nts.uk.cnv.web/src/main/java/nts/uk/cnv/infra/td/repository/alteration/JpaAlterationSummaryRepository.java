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
					+ " where v." + NemTdAlterationView.jpqlWhere(devProgress)
					+ " order by v.time asc";
		
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
					+ " where v.alterationId in :alterationId"
							+ " order by v.time asc";
		
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
				.setParameter("alterationId", alterId)
				.getList(e -> e.toDomain());
	}
	
	@Override
	public List<AlterationSummary> getByFeature(String featureId) {
		String jpql = BaseSelect
					+ " where v.featureId=:featureId"
							+ " order by v.time asc";
		
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("featureId", featureId)
			.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getByFeature(String featureId, DevelopmentStatus devStatus) {
		String jpql = BaseSelect
					+ " where v.featureId=:featureId"
					+ " and " + NemTdAlterationView.jpqlWhere(devStatus, "v")
					+ " order by v.time asc";
		
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("featureId", featureId)
			.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress) {
		String jpql = BaseSelect
					+ " where v.featureId=:featureId"
					+ " and v." + NemTdAlterationView.jpqlWhere(devProgress)
					+ " order by v.time asc";
		
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("featureId", featureId)
			.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress) {
		String jpql = BaseSelect
					+ " where v.tableId=:tableId"
					+ " and v." + NemTdAlterationView.jpqlWhere(devProgress)
					+ " order by v.time asc";
		
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("tableId", tableId)
			.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getOlder(AlterationSummary alter, DevelopmentProgress devProgress) {
		String jpql = BaseSelect
				+ " where v.featureId=:featureId"
				+ " and v.time<:time"
				+ " and v." + NemTdAlterationView.jpqlWhere(devProgress)
				+ " order by v.time asc";
		return this.queryProxy().query(jpql, NemTdAlterationView.class)
			.setParameter("featureId", alter.getFeatureId())
			.setParameter("time", alter.getTime())
			.getList(e -> e.toDomain());
	}

	@Override
	public List<AlterationSummary> getByEvent(String eventId, DevelopmentStatus status) {
		String jpql = BaseSelect
				+ " where v." + NemTdAlterationView.getField(status) + "=:eventId"
				+ " and " + NemTdAlterationView.jpqlWhere(status, "v")
				+ " order by v.time asc";

		return this.queryProxy().query(jpql, NemTdAlterationView.class)
				.setParameter("eventId", eventId)
				.getList(entity -> entity.toDomain());
	}

	@Override
	public List<AlterationSummary> getByEvent(String eventId, DevelopmentProgress devProgress) {
		String jpql = BaseSelect
				+ " where v." + NemTdAlterationView.getField(devProgress.getBaseline()) + "=:eventId"
				+ " and v." + NemTdAlterationView.jpqlWhere(devProgress)
				+ " order by v.time asc";

		return this.queryProxy().query(jpql, NemTdAlterationView.class)
				.setParameter("eventId", eventId)
				.getList(entity -> entity.toDomain());
	}
}
