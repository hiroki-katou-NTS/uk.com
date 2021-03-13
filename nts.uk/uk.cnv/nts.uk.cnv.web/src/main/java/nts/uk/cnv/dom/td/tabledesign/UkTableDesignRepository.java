package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.app.cnv.dto.GetUkTablesResultDto;

public interface UkTableDesignRepository {

	void insert(Snapshot tableDesign);
	void update(Snapshot tableDesign);
	boolean exists(String tableName);

	List<TableDesign> getByTableName(String tablename);

	Optional<TableDesign> findByKey(String tablename, String feature, String eventId);
	List<TableDesign> getAll(String featuer, String eventId);
	List<GetUkTablesResultDto> getAllTableList(String feature, String eventId);
	String getNewestSsEventId(String featureId);
}
