package nts.uk.cnv.dom.tabledesign;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.app.dto.GetUkTablesDto;

public interface UkTableDesignRepository {

	void insert(TableDesign tableDesign);
	void update(TableDesign tableDesign);
	boolean exists(String tableName);

	Optional<TableDesign> find(String tablename);
	public List<GetUkTablesDto> getAllTableList();
}
