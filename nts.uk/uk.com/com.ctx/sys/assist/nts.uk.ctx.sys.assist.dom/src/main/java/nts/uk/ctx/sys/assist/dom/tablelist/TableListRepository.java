package nts.uk.ctx.sys.assist.dom.tablelist;

import java.util.List;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface TableListRepository {
	
	void add(TableList domain);
	void update(TableList domain);
	void remove(TableList domain);
	List<TableList> getByOffsetAndNumber(String storeProcessingId, int offset, int number);
	List<TableList> getByProcessingId(String storeProcessingId);
	List<TableList> getBySystemTypeAndStorageId(int systemType,String StorageId);
	List<TableList> getBySystemTypeAndRecoverId(int systemType,String recoverId);
	void getDataDynamic(TableList tableList, List<String> targetEmployeesSid, List<String> headerCsv3, FileGeneratorContext generatorContext);
	List<String> getAllColumnName(String tableName);
	boolean isPresent(TableList domain);
	void add2(TableList domain);
	void update2(TableList domain);
}
