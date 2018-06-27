package nts.uk.ctx.sys.assist.dom.tablelist;

import java.util.List;

public interface TableListRepository {
	
	void add(TableList domain);
	void update(TableList domain);
	void updateByStorageId(String storageId, String recoveryId);
	void updateOldDataFieldByStorageId(String storageId, int oldDataValue);
	Class<?> getTypeForTableName(String tableName);
	String getFieldForColumnName(Class<?> tableType, String columnName);
	List<TableList> getByOffsetAndNumber(String storeProcessingId, int offset, int number);
	List<TableList> getByProcessingId(String storeProcessingId);
	List<List<String>> getDataDynamic(TableList tableList);
	List<String> getAllColumnName(String tableName);
}
