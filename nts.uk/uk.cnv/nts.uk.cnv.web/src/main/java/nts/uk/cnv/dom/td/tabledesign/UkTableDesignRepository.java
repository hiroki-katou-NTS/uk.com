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

	Optional<TableDesign> findByKey(String tablename, String feature, GeneralDateTime date);
	List<TableDesign> getAll(String featuer, GeneralDateTime date);
	List<GetUkTablesResultDto> getAllTableList(String feature, GeneralDateTime date);
}
