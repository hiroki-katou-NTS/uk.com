package nts.uk.cnv.dom.td.schema.tabledesign;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;

public interface ErpTableDesignRepository {

	void insert(TableSnapshot ss);
	void update(TableSnapshot ss);
	boolean exists(String tableName);

	Optional<TableDesign> find(String tablename);
	public List<String> getAllTableList();
}
