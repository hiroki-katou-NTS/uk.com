package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;
import java.util.Optional;

public interface ErpTableDesignRepository {

	void insert(TableDesign tableDesign);
	void update(TableDesign tableDesign);
	boolean exists(String tableName);

	Optional<TableDesign> find(String tablename);
	public List<String> getAllTableList();
}
