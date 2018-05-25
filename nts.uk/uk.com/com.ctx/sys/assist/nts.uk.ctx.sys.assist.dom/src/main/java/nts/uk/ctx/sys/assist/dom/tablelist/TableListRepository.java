package nts.uk.ctx.sys.assist.dom.tablelist;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface TableListRepository {
	
	void add(TableList domain);
	List<?> getAutoObject(String query, Class<?> type, GeneralDate startDate, GeneralDate endDate);
	Class<?> getTypeForTableName(String tableName);
	String getFieldForColumnName(Class<?> tableType, String columnName);
}
