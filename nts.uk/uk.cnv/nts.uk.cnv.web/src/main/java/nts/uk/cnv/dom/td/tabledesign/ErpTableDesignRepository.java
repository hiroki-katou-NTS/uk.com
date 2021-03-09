package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;
import java.util.Optional;

public interface ErpTableDesignRepository {

	void insert(Snapshot ss);
	void update(Snapshot ss);
	boolean exists(String tableName);

	Optional<TableDesign> find(String tablename);
	public List<String> getAllTableList();
}
