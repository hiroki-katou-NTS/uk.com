package nts.uk.cnv.infra.td.repository;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlteration;

public class JpaAlterationRepository extends JpaRepository implements AlterationRepository {

	@Override
	public List<Alteration> getTableListChange() {
		final String sql = ""
				+ " SELECT alt"
				+ " FROM NemTdAlteration alt"
				+ " INNER JOIN NemTdAlterationView view ON alt.alterationId = view.alterationId"
				+ " WHERE view.accepted = :accepted";

		return this.queryProxy().query(sql, NemTdAlteration.class)
				.setParameter("accepted", false)
				.getList(entity -> entity.toDomain()).stream()
				.filter(alt -> alt.getContents().stream().anyMatch(c -> c.getType().isAffectTableList()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Alteration> getUnaccepted(String tableId) {
		final String sql = "SELECT alt"
				+ " FROM NemTdAlteration alt"
				+ " INNER JOIN NemTdAlterationView view ON alt.alterationId = view.alterationId"
				+ " WHERE view.tableId = :tableId"
				+ " AND view.accepted = :accepted";

		return this.queryProxy().query(sql, NemTdAlteration.class)
				.setParameter("tableId", tableId)
				.setParameter("accepted", false)
				.getList(entity -> entity.toDomain());
	}

	@Override
	public void insert(Alteration alt) {
		this.commandProxy().insert(NemTdAlteration.toEntity(alt));
	}

	@Override
	public List<AlterationSummary> getAllUndeliveled(String featureId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getOlderUndeliveled(String alterId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getAllUnaccepted(String featureId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<AlterationSummary> getOlderUnaccepted(String alterId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
