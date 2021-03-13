package nts.uk.cnv.dom.td.schema.tabledesign;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.dom.td.schema.snapshot.Snapshot;

public interface ErpTableDesignRepository {

	void insert(Snapshot ss);
	void update(Snapshot ss);
	boolean exists(String tableName);

	Optional<TableDesign> find(String tablename);
	public List<String> getAllTableList();
}
