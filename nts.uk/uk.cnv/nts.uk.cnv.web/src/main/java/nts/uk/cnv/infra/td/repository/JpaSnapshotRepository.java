package nts.uk.cnv.infra.td.repository;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.schema.snapshot.Snapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

public class JpaSnapshotRepository extends JpaRepository implements SnapshotRepository {

	@Override
	public Snapshot getNewest(String tableId) {
//		String sql = "SELECT td FROM ScvmtUkTableDesign td"
//			+ " WHERE td.pk.tableId = :tableId"
//			+ " ORDER BY td.";
//		list = this.queryProxy().query(sql, ScvmtUkTableDesign.class)
//				.setParameter("tableId", tableId)
//				.getList(td -> td.toDomain());
//
//
//		Map<String, List<ScvmtUkTableDesign>> map = list.stream()
//				.collect(Collectors.groupingBy(rec -> rec.pk.getTableId()));
//
//		List<TableDesign> result = map.values().stream()
//			.map(rec -> rec.stream().findFirst().get().toDomain())
//			.collect(Collectors.toList());
//
//		return result;
		// TODO 自動生成されたメソッド・スタブ
		return new Snapshot();
	}

	@Override
	public List<TableDesign> getNewestAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
