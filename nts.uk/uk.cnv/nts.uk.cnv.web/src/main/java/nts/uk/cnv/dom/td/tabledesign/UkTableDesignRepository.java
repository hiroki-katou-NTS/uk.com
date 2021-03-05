package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.app.dto.GetUkTablesResultDto;

public interface UkTableDesignRepository {

	void insert(Snapshot tableDesign);
	void update(Snapshot tableDesign);
	boolean exists(String tableName);

	List<TableDesign> getByTableName(String tablename);

	Optional<TableDesign> findByKey(String tablename, String branch, GeneralDateTime date);
	//List<TableDesign> find(String tableId, String branch, GeneralDate date);
	List<TableDesign> getAll(String branch, GeneralDateTime date);
	List<GetUkTablesResultDto> getAllTableList(String branch, GeneralDateTime date);
}
