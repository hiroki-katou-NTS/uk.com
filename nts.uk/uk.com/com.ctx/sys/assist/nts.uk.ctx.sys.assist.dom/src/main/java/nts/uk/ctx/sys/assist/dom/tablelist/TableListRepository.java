package nts.uk.ctx.sys.assist.dom.tablelist;

import java.util.List;

public interface TableListRepository {
	
	void add(TableList domain);
	Class<?> getTypeForTableName(String tableName);
	String getFieldForColumnName(Class<?> tableType, String columnName);
	List<TableList> getByOffsetAndNumber(int offset, int number);
	List<TableList> getAllTableList();
	List<?> getDataDynamic(TableList tableList);
}
