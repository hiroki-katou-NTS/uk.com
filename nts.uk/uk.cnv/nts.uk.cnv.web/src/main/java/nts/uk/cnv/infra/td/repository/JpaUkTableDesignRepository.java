package nts.uk.cnv.infra.td.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.app.cnv.dto.GetUkTablesResultDto;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.Indexes;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.UkTableDesignRepository;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.infra.td.entity.uktabledesign.ScvmtUkColumnDesign;
import nts.uk.cnv.infra.td.entity.uktabledesign.ScvmtUkColumnDesignPk;
import nts.uk.cnv.infra.td.entity.uktabledesign.ScvmtUkIndexColumns;
import nts.uk.cnv.infra.td.entity.uktabledesign.ScvmtUkIndexColumnsPk;
import nts.uk.cnv.infra.td.entity.uktabledesign.ScvmtUkIndexDesign;
import nts.uk.cnv.infra.td.entity.uktabledesign.ScvmtUkIndexDesignPk;
import nts.uk.cnv.infra.td.entity.uktabledesign.ScvmtUkTableDesign;
import nts.uk.cnv.infra.td.entity.uktabledesign.ScvmtUkTableDesignPk;

@Stateless
public class JpaUkTableDesignRepository extends JpaRepository implements UkTableDesignRepository {

	@Override
	public void insert(TableSnapshot tableDesign) {
		this.commandProxy().insert(toEntity(tableDesign));
	}

	@Override
	public void update(TableSnapshot tableDesign) {
		ScvmtUkTableDesign tergetEntity = toEntity(tableDesign);
		Optional<ScvmtUkTableDesign> before = this.queryProxy().find(
				tergetEntity.pk, ScvmtUkTableDesign.class);

		if(before.isPresent()) {
			this.commandProxy().remove(before);
		}

		this.commandProxy().insert(toEntity(tableDesign));
	}

	@Override
	public boolean exists(String tableName) {
		String sql = "SELECT td FROM ScvmtUkTableDesign td WHERE td.name = :name";
		List<ScvmtUkTableDesign> result = this.queryProxy().query(sql, ScvmtUkTableDesign.class)
				.setParameter("name", tableName)
				.getList();
		return (result.size() > 0);
	}

	private ScvmtUkTableDesign toEntity(TableSnapshot tableDesign) {
		List<ScvmtUkColumnDesign> columns = tableDesign.getColumns().stream()
				.map(cd -> toEntity(tableDesign, cd))
				.collect(Collectors.toList());

		List<ScvmtUkIndexDesign> indexes = new ArrayList<>();
//		for (Indexes idx: tableDesign.getIndexes()) {
//			List<ScvmtUkIndexColumns> indexcolumns = idx.getColumns().stream()
//				.map(col -> new ScvmtUkIndexColumns(
//						new ScvmtUkIndexColumnsPk(
//								tableDesign.getId(),
//								tableDesign.getSnapshotId(),
//								"",
//								idx.getName(),
//								idx.getColumns().indexOf(col),
//								col),
//						null)
//					)
//				.collect(Collectors.toList());
//
//			indexes.add(new ScvmtUkIndexDesign(
//					new ScvmtUkIndexDesignPk(
//							tableDesign.getId(), tableDesign.getSnapshotId(), "", idx.getName()),
//					idx.getConstraintType(),
//					idx.isClustered(),
//					indexcolumns,
//					null
//			));
//		}

		return new ScvmtUkTableDesign(
				new ScvmtUkTableDesignPk(
					tableDesign.getId(),
					tableDesign.getSnapshotId()),
				tableDesign.getName(),
				tableDesign.getJpName(),
				columns,
				indexes);
	}

	private ScvmtUkColumnDesign toEntity(TableSnapshot tableDesign, ColumnDesign columnDesign) {
		return new ScvmtUkColumnDesign(
					new ScvmtUkColumnDesignPk(
							tableDesign.getId(),
							tableDesign.getSnapshotId(),
							columnDesign.getId()),
					columnDesign.getName(),
					columnDesign.getJpName(),
					columnDesign.getType().getDataType().toString(),
					columnDesign.getType().getLength(),
					columnDesign.getType().getScale(),
					(columnDesign.getType().isNullable() ? 1 : 0),
					columnDesign.getType().getDefaultValue(),
					columnDesign.getComment(),
					columnDesign.getType().getCheckConstaint(),
					columnDesign.getDispOrder(),
					null
				);
	}

	@Override
	@SneakyThrows
	public Optional<TableDesign> findByKey(String tableId, String snapshotId) {
		Optional<ScvmtUkTableDesign> result = find(tableId, snapshotId);
		return Optional.of(result.get().toDomain());
	}

	private Optional<ScvmtUkTableDesign> find(String tableId, String snapshotId) {
		return this.queryProxy().find(
				new ScvmtUkTableDesignPk(tableId, snapshotId),
				ScvmtUkTableDesign.class);
	}

	@Override
	public List<GetUkTablesResultDto> getAllTableList(String feature, String eventId) {
		return getAll(feature, eventId).stream()
			.map(td -> new GetUkTablesResultDto(td.getId(), td.getName()))
			.collect(Collectors.toList());
	}

	@Override
	public List<TableDesign> getAll(String feature, String eventId) {
		String sql;
		List<ScvmtUkTableDesign> list;
		if (feature != null && !feature.isEmpty()) {
			sql = "SELECT td FROM ScvmtUkTableDesign td"
				+ " WHERE td.pk.feature = :feature"
				+ " AND   td.pk.eventId = :eventId";
			list = this.queryProxy().query(sql, ScvmtUkTableDesign.class)
					.setParameter("feature", feature)
					.setParameter("eventId", eventId)
					.getList();
		}
		else {
			sql = "SELECT td FROM ScvmtUkTableDesign td"
				+ " WHERE td.pk.eventId = :eventId";
			list = this.queryProxy().query(sql, ScvmtUkTableDesign.class)
					.setParameter("eventId", eventId)
					.getList();
		}

		Map<String, List<ScvmtUkTableDesign>> map = list.stream()
				.collect(Collectors.groupingBy(rec -> rec.pk.getTableId()));

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
		List<TableDesign> result = this.queryProxy().query(sql, ScvmtUkTableDesign.class)
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
