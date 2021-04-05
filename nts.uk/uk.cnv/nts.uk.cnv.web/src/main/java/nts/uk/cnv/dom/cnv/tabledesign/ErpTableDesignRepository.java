package nts.uk.cnv.dom.cnv.tabledesign;

import java.util.List;
import java.util.Optional;

public interface ErpTableDesignRepository {

	void insert(ErpTableDesign tableDesign);
	void update(ErpTableDesign tableDesign);
	boolean exists(String tableName);

	Optional<ErpTableDesign> find(String tablename);
	public List<String> getAllTableList();
}
