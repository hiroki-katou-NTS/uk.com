package nts.uk.cnv.dom.tabledesign;

import java.util.Optional;

public interface TableDesignRepository {

	void insert(TableDesign tableDesign);
	void update(TableDesign tableDesign);
	void delete(TableDesign tableDesign);
	boolean exists(String tableName);
	void rename(String befor, String after);
	
	Optional<TableDesign> find(String tablename);
}
