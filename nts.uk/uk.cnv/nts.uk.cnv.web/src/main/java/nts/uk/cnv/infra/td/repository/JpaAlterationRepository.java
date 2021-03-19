package nts.uk.cnv.infra.td.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlteration;

public class JpaAlterationRepository extends JpaRepository implements AlterationRepository {

	@Override
	public Alteration get(String alterationId) {
		return this.queryProxy().find(alterationId, NemTdAlteration.class)
				.map(entity -> entity.toDomain())
				.get();
	}

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

	private static final Map<DevelopmentStatus, String> EventColumns;
	static {
		EventColumns = new HashMap<>();
		EventColumns.put(DevelopmentStatus.ORDERED, "orderedEventId");
		EventColumns.put(DevelopmentStatus.DELIVERED, "deliveredEventId");
		EventColumns.put(DevelopmentStatus.ACCEPTED, "acceptedEventId");
	}

	@Override
	public List<Alteration> getTable(String tableId, DevelopmentProgress progress) {

		String jpql = "SELECT alt"
				+ " FROM NemTdAlteration alt"
				+ " INNER JOIN NemTdAlterationView view ON alt.alterationId = view.alterationId"
				+ " WHERE view.tableId = :tableId"
				+ " AND view."
				+ EventColumns.get(progress.getBaseline())
				+ " is " + (progress.isAchieved() ? "" : "not") + " null";

		return this.queryProxy().query(jpql, NemTdAlteration.class)
				.setParameter("tableId", tableId)
				.getList(entity -> entity.toDomain());
	}

	@Override
	public void insert(Alteration alt) {
		this.commandProxy().insert(NemTdAlteration.toEntity(alt));
	}
}
