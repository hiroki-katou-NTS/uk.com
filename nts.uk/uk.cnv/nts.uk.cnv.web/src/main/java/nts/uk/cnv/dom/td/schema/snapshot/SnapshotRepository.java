package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.Optional;

public interface SnapshotRepository {

	Optional<SchemaSnapshot> getSchemaLatest();
	
	TableListSnapshot getTableList(String snapshotId);

}
