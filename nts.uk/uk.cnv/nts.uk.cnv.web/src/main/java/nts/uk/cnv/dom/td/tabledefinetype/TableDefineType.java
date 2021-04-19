package nts.uk.cnv.dom.td.tabledefinetype;

import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;

public interface TableDefineType {
	// 型
	public String dataType(DataType type, Integer... length);
	public DataType parse(String type, Integer... length) ;

	public String tableCommentDdl(String tableName, String jpName);
	public String columnCommentDdl(String tableName, String columnName, String jpName);
	public String rlsDdl(String tableName);
	public String convertBoolValue(String value);
	public String renameColumnDdl(String tableName, String beforeColumnName,String columnName);
}
