package nts.uk.cnv.dom.databasetype;

public class UkDataType implements DataTypeDefine {

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
}
