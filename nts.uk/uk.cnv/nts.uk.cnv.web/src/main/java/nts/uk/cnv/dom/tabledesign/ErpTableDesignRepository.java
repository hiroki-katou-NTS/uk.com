package nts.uk.cnv.dom.tabledesign;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.core.dom.tabledesign.ErpTableDesign;

public interface ErpTableDesignRepository {

	void insert(ErpTableDesign tableDesign);
	void update(ErpTableDesign tableDesign);
	boolean exists(String tableName);

	Optional<ErpTableDesign> find(String tablename);
	List<String> getAllTableList();
	List<String> getPkColumns(String tablename);
}
