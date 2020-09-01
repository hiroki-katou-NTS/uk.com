package nts.uk.cnv.dom.tabledesign;

import java.util.Optional;

public interface TableDesignRepository {

	void insert(TableDesign tableDesign);
	void update(TableDesign tableDesign);
	void delete(TableDesign tableDesign);
	boolean exists(String tableName);

	Optional<TableDesign> find(String tablename);
}
