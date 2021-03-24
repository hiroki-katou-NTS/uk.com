package nts.uk.cnv.infra.td.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.cnv.dom.td.schema.TableIdentity;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableListSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.infra.td.entity.snapshot.NemTdSnapShotSchema;
import nts.uk.cnv.infra.td.entity.snapshot.NemTdSnapshotTable;
import nts.uk.cnv.infra.td.entity.snapshot.column.NemTdSnapshotColumn;
import nts.uk.cnv.infra.td.entity.snapshot.index.NemTdSnapshotTableIndex;
import nts.uk.cnv.infra.td.entity.snapshot.index.NemTdSnapshotTableIndexColumns;
import nts.uk.cnv.infra.td.entity.snapshot.index.NemTdSnapshotTableIndexPk;

@Stateless
public class JpaSnapshotRepository extends JpaRepository implements SnapshotRepository {

	@Override
	public Optional<SchemaSnapshot> getSchemaLatest() {
		
		String sql = "select * from NEM_TD_SNAPSHOT_SCHEMA"
				+ " order by GENERATED_AT desc";
		
		return this.jdbcProxy()
				.query(sql)
				.getList(rec -> new SchemaSnapshot(
						rec.getString("SNAPSHOT_ID"),
						rec.getGeneralDateTime("GENERATED_AT"),
						rec.getString("SOURCE_EVENT_ID")))
				.stream()
				.findFirst();
	}

	@Override
	public TableListSnapshot getTableList(String snapshotId) {

		String sql = "select * from NEM_TD_SNAPSHOT_TABLE"
				+ " where SNAPSHOT_ID = @ssid";
		
		val tables = this.jdbcProxy()
				.query(sql)
				.paramString("ssid", snapshotId)
				.getList(rec -> new TableIdentity(
						rec.getString("TABLE_ID"),
						rec.getString("NAME")));
		
		return new TableListSnapshot(snapshotId, tables);
	}
	
	private <E> List<E> getEntitiesByTable(
			String snapshotId,
			String tableId,
			String targetTableName,
			JpaEntityMapper<E> mapper) {
		
		String sql = "select * from " + targetTableName
				+ " where SNAPSHOT_ID = @ssid"
				+ " and TABLE_ID = @tid";
		return this.jdbcProxy().query(sql)
				.paramString("ssid", snapshotId)
				.paramString("tid", tableId)
				.getList(rec -> mapper.toEntity(rec));
	}

	@Override
	public Optional<TableSnapshot> getTable(String snapshotId, String tableId) {
		
		val columns = getTableColumnsBy(snapshotId, tableId);
		
		val indexColumns = getIndexColumnsBy(snapshotId, tableId);
		
		val indexes = getIndexesBy(snapshotId, tableId);
		indexes.forEach(index -> {
			index.columns = indexColumns.get(index.pk);
		});
		
		return this.getEntitiesByTable(snapshotId, tableId, "NEM_TD_SNAPSHOT_TABLE", NemTdSnapshotTable.MAPPER)
				.stream()
				.findFirst()
				.map(t -> {
					t.columns = columns;
					t.indexes = indexes;
					return new TableSnapshot(snapshotId, t.toDomain());
				});
	}

	private List<NemTdSnapshotTableIndex> getIndexesBy(String snapshotId, String tableId) {
		return this.getEntitiesByTable(snapshotId, tableId, "NEM_TD_SNAPSHOT_TABLE_INDEX", NemTdSnapshotTableIndex.MAPPER);
	}

	private Map<NemTdSnapshotTableIndexPk, List<NemTdSnapshotTableIndexColumns>> getIndexColumnsBy(String snapshotId,String tableId) {
		return this
				.getEntitiesByTable(snapshotId, tableId, "NEM_TD_SNAPSHOT_TABLE_INDEX_COLUMNS", NemTdSnapshotTableIndexColumns.MAPPER)
				.stream()
				.collect(Collectors.groupingBy(e -> e.pk.parentPk()));
	}

	private List<NemTdSnapshotColumn> getTableColumnsBy(String snapshotId, String tableId) {
		return this.getEntitiesByTable(snapshotId, tableId, "NEM_TD_SNAPSHOT_COLUMN", NemTdSnapshotColumn.MAPPER);
	}

	@Override
	public List<TableSnapshot> getTablesLatest() {
		val schemaSnapShot = getSchemaLatest();
		String sql = "select * from NEM_TD_SNAPSHOT_TABLE"
				+ " where SNAPSHOT_ID = @ssid";
		return this.jdbcProxy().query(sql)
				.paramString("ssid", schemaSnapShot.get().getSnapshotId())
				.getList(rec ->{
					val tableId = rec.getString("TABLE_ID");
					val columns = getTableColumnsBy(schemaSnapShot.get().getSnapshotId(), tableId);
					val indexColumns = getIndexColumnsBy(schemaSnapShot.get().getSnapshotId(), tableId);
					val indexes = getIndexesBy(schemaSnapShot.get().getSnapshotId(), tableId);
					indexes.forEach(index -> {
						index.columns = indexColumns.get(index.pk);
					});
					NemTdSnapshotTable entity = NemTdSnapshotTable.MAPPER.toEntity(rec);
					entity.columns = columns;
					entity.indexes = indexes;
					return new TableSnapshot(schemaSnapShot.get().getSnapshotId(), entity.toDomain());
				});
	}
	
	@Override
	public void regist(SchemaSnapshot snapShot) {
		this.commandProxy().insert(NemTdSnapShotSchema.toEntity(snapShot));
	}
	@Override
	public void regist(String snapshotId, List<TableDesign> snapShots) {
		this.commandProxy().insertAll(NemTdSnapshotTable.toEntities(snapshotId, snapShots));
	}
}
