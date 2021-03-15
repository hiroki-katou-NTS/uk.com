package nts.uk.cnv.dom.td.schema.tabledesign;

import java.util.List;
import java.util.Optional;

import nts.uk.cnv.app.cnv.dto.GetUkTablesResultDto;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;

public interface UkTableDesignRepository {

	void insert(TableSnapshot tableDesign);
	void update(TableSnapshot tableDesign);
	boolean exists(String tableName);

	List<TableDesign> getByTableName(String tablename);

	Optional<TableDesign> findByKey(String tablename, String snapshotId, String eventId);
	List<TableDesign> getAll(String featuer, String eventId);
	List<GetUkTablesResultDto> getAllTableList(String feature, String eventId);
	String getNewestSsEventId(String featureId);
}
