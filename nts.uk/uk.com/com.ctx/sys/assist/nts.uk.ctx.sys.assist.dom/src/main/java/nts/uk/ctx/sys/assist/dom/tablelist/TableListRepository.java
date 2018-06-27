package nts.uk.ctx.sys.assist.dom.tablelist;

import java.util.List;

public interface TableListRepository {
	
	void add(TableList domain);
	void update(TableList domain);
	void updateByStorageId(String storageId, String recoveryId);
	void updateOldDataFieldByStorageId(String storageId, int oldDataValue);
	List<TableList> getByOffsetAndNumber(String storeProcessingId, int offset, int number);
	List<TableList> getByProcessingId(String storeProcessingId);
	List<List<String>> getDataDynamic(TableList tableList);
	List<String> getAllColumnName(String tableName);
}
