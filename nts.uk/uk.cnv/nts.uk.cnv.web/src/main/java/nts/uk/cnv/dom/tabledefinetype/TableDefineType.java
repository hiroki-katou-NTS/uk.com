package nts.uk.cnv.dom.tabledefinetype;

public interface TableDefineType {
	// 型
	public String dataType(DataType type, Integer... length);
	public DataType parse(String type, Integer... length) ;

	public String tableCommentDdl(String tableName, String comment);
	public String columnCommentDdl(String tableName, String columnName, String comment);
	public String rlsDdl(String tableName);
}
