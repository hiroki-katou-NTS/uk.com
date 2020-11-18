package nts.uk.cnv.dom.tabledefinetype;

public class UkDataType implements TableDefineType {

	public String dataType(DataType type, Integer... length) {
		switch (type) {
		case BOOL:
			return "BOOL";
		case INT:
			return String.format("INT(%d)", (Object[]) length);
		case REAL:
			return String.format("REAL(%d,%d)", (Object[]) length);
		case CHAR:
			return String.format("CHAR(%d)", (Object[]) length);
		case VARCHAR:
			return String.format("VARCHAR(%d)", (Object[]) length);
		case NCHAR:
			return String.format("NCHAR(%d)", (Object[]) length);
		case NVARCHAR:
			return String.format("NVARCHAR(%d)", (Object[]) length);
		case DATE:
			return "DATE";
		case DATETIME:
			return "DATETIME";
		case DATETIMEMS:
			return "DATETIMEMS";
		case GUID:
			return "GUID";
		default:
			break;
		}
		throw new IllegalArgumentException();
	}

	@Override
	public DataType parse(String type, Integer... length) {
		return DataType.valueOf(type);
	}

	@Override
	public String tableCommentDdl(String tableName, String comment) {
		if(comment == null || comment.isEmpty()) return "";

		return "COMMENT ON TABLE " + tableName + " IS '" + comment + "';";
	}

	@Override
	public String columnCommentDdl(String tableName, String columnName, String comment) {
		if(comment == null || comment.isEmpty()) return "";

		return "COMMENT ON COLUMN " + tableName + "." + columnName + " IS '" + comment + "';";
	}

	@Override
	public String rlsDdl(String tableName) {
		return "";
	}
}
