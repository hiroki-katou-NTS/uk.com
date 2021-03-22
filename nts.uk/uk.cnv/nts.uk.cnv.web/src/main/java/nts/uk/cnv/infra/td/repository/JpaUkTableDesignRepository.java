package nts.uk.cnv.infra.td.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.app.cnv.dto.GetUkTablesResultDto;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.UkTableDesignRepository;
import nts.uk.cnv.infra.td.entity.schema.snapshot.NemTdSnapshotTable;
import nts.uk.cnv.infra.td.entity.schema.snapshot.NemTdSnapshotTablePk;

@Stateless
public class JpaUkTableDesignRepository extends JpaRepository implements UkTableDesignRepository {

	@Override
	public void insert(TableSnapshot tableDesign) {
		this.commandProxy().insert(toEntity(tableDesign));
	}

	@Override
	public void update(TableSnapshot tableDesign) {
		NemTdSnapshotTable tergetEntity = toEntity(tableDesign);
		Optional<NemTdSnapshotTable> before = this.queryProxy().find(
				tergetEntity.pk, NemTdSnapshotTable.class);

		if(before.isPresent()) {
			this.commandProxy().remove(before);
		}

		this.commandProxy().insert(toEntity(tableDesign));
	}

	@Override
	public boolean exists(String tableName) {
		String sql = "SELECT td FROM ScvmtUkTableDesign td WHERE td.name = :name";
		List<NemTdSnapshotTable> result = this.queryProxy().query(sql, NemTdSnapshotTable.class)
				.setParameter("name", tableName)
				.getList();
		return (result.size() > 0);
	}

	private NemTdSnapshotTable toEntity(TableSnapshot tableDesign) {
		boolean a = true;
		if (a) throw new RuntimeException();
		return null;
//		List<NemTdSnapshotColumn> columns = tableDesign.getColumns().stream()
//				.map(cd -> toEntity(tableDesign, cd))
//				.collect(Collectors.toList());
//
//		List<NemTdSnapshotTableIndex> indexes = new ArrayList<>();
		
//		PrimaryKey pk = tableDesign.getConstraints().getPrimaryKey();
//		indexes.add(
//			NemTdSnapshotTableIndex.toEntityFromPk(
//				tableDesign.getId(),
//				tableDesign.getSnapshotId(),
//				pk
//			)
//		);
//
//		List<UniqueConstraint> uks = tableDesign.getConstraints().getUniqueConstraints();
//		indexes.addAll(
//			NemTdSnapshotTableIndex.toEntityFromUk(
//					tableDesign.getId(),
//					tableDesign.getSnapshotId(),
//					uks)
//		);
//
//		List<TableIndex> index = tableDesign.getConstraints().getIndexes();
//		indexes.addAll(
//			NemTdSnapshotTableIndex.toEntityFromIndex(
//					tableDesign.getId(),
//					tableDesign.getSnapshotId(),
//					index)
//		);
//
//		return new NemTdSnapshotTable(
//				new NemTdSnapshotTablePk(
//					tableDesign.getId(),
//					tableDesign.getSnapshotId()),
//				tableDesign.getName().v(),
//				tableDesign.getJpName(),
//				columns,
//				indexes);
	}

//	private NemTdSnapshotColumn toEntity(TableSnapshot tableDesign, ColumnDesign columnDesign) {
//		return new NemTdSnapshotColumn(
//					new NemTdSnapshotColumnPk(
//							tableDesign.getId(),
//							tableDesign.getSnapshotId(),
//							columnDesign.getId()),
//					columnDesign.getName(),
//					columnDesign.getJpName(),
//					columnDesign.getType().getDataType().toString(),
//					columnDesign.getType().getLength(),
//					columnDesign.getType().getScale(),
//					(columnDesign.getType().isNullable() ? 1 : 0),
//					columnDesign.getType().getDefaultValue(),
//					columnDesign.getComment(),
//					columnDesign.getType().getCheckConstaint(),
//					columnDesign.getDispOrder(),
//					null
//				);
//	}

	@Override
	@SneakyThrows
	public Optional<TableDesign> findByKey(String snapshotId, String tableId) {
		Optional<NemTdSnapshotTable> result = find(snapshotId, tableId);
		return result.map(r -> r.toDomain());
	}

	private Optional<NemTdSnapshotTable> find(String snapshotId, String tableId) {
		return this.queryProxy().find(
				new NemTdSnapshotTablePk(snapshotId, tableId),
				NemTdSnapshotTable.class);
	}

	@Override
	public List<GetUkTablesResultDto> getAllTableList(String feature, String eventId) {
		return getAll(feature, eventId).stream()
			.map(td -> new GetUkTablesResultDto(td.getId(), td.getName().v()))
			.collect(Collectors.toList());
	}

	@Override
	public List<TableDesign> getAll(String feature, String eventId) {
		String sql;
		List<NemTdSnapshotTable> list;
		if (feature != null && !feature.isEmpty()) {
			sql = "SELECT td FROM ScvmtUkTableDesign td"
				+ " WHERE td.pk.feature = :feature"
				+ " AND   td.pk.eventId = :eventId";
			list = this.queryProxy().query(sql, NemTdSnapshotTable.class)
					.setParameter("feature", feature)
					.setParameter("eventId", eventId)
					.getList();
		}
		else {
			sql = "SELECT td FROM ScvmtUkTableDesign td"
				+ " WHERE td.pk.eventId = :eventId";
			list = this.queryProxy().query(sql, NemTdSnapshotTable.class)
					.setParameter("eventId", eventId)
					.getList();
		}

		Map<String, List<NemTdSnapshotTable>> map = list.stream()
				.collect(Collectors.groupingBy(rec -> rec.pk.tableId));

		List<TableDesign> result = map.values().stream()
			.map(rec -> rec.stream().findFirst().get().toDomain())
			.collect(Collectors.toList());

		return result;
	}

	@Override
	public List<TableDesign> getByTableName(String tablename) {
		String sql = "SELECT td FROM ScvmtUkTableDesign td "
				+ " WHERE td.name = :name"
				+ " ORDER BY td.pk.feature, td.pk.date DESC";
		List<TableDesign> result = this.queryProxy().query(sql, NemTdSnapshotTable.class)
				.setParameter("name", tablename)
				.getList(rec -> rec.toDomain());

		return result;
	}

	@Override
	public String getNewestSsEventId(String featureId) {
		// TODO 自動生成されたメソッド・スタブ
		return "00000000";
	}
}
