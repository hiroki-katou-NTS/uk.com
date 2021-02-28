package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.cnv.app.dto.GetUkTablesResultDto;

public interface UkTableDesignRepository {

	void insert(TableDesign tableDesign);
	void update(TableDesign tableDesign);
	boolean exists(String tableName);

	List<TableDesign> getByTableName(String tablename);

	Optional<TableDesign> findByKey(String tablename, String branch, GeneralDate date);
	//List<TableDesign> find(String tableId, String branch, GeneralDate date);
	List<TableDesign> getAll(String branch, GeneralDate date);
	List<GetUkTablesResultDto> getAllTableList(String branch, GeneralDate date);
}
