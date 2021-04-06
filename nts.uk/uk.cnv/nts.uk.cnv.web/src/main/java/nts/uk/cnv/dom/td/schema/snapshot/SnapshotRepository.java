package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

public interface SnapshotRepository {

	Optional<SchemaSnapshot> getSchemaLatest();

	TableListSnapshot getTableList(String snapshotId);

	List<TableSnapshot> getTablesLatest();

	Optional<TableSnapshot> getTable(String snapshotId, String tableId);

	Optional<TableSnapshot> getTableByName(String snapshotId, String tableName);

	void regist(SchemaSnapshot snapShot);

	void regist(String snapshotId, List<TableDesign> snapShots);
}
