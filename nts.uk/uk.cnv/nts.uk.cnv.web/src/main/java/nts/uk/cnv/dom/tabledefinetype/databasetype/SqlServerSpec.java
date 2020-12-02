package nts.uk.cnv.dom.tabledefinetype.databasetype;

import java.util.List;

import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.tabledefinetype.DataType;
import nts.uk.cnv.dom.tabledefinetype.DatabaseSpec;

@NoArgsConstructor
public class SqlServerSpec implements DatabaseSpec{

	/** 照合順序 **/
    public static final String collateType = "Japanese_XJIS_100_BIN2";

	public String dataType(DataType type, Integer... length) {
		switch (type) {
		case BOOL:
			return "DECIMAL(1)";
		case INT:
			return String.format("DECIMAL(%d)", (Object[]) length);
		case REAL:
			return String.format("DECIMAL(%d,%d)", (Object[]) length);
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
			return "DATETIME2";
		case DATETIMEMS:
			return "DATETIME2(3)";
		case GUID:
			return "CHAR(36)";
		default:
			break;
		}
		throw new IllegalArgumentException();
	}

	public String param(String expression) {
		return "@" + expression;
	}

	public String declaration(String pramName, DataType type, Integer... length) {
		return String.format("DECLARE @%s %s;", pramName, this.dataType(type, length));
	}

	public String initialization(String pramName, String value) {
		return String.format("SET @%s = '%s';", pramName, value);
	}

	public String initialization(String pramName, int value) {
		return String.format("SET @%s = %d;", pramName, value);
	}

	public String cast(String expression, DataType type, Integer... length) {
		return String.format("CAST(%s AS %s)", expression, this.dataType(type, length));
	}

	public String newUuid() {
		return "NEWID()";
	}

	public String concat(String expression1, String expression2) {
		return String.format("CONCAT(%s,%s)", expression1, expression2);
	}

	public String left(String expression, int length) {
		return String.format("LEFT(%s,%d)", expression, length);
	}

	public String right(String expression, int length) {
		return String.format("RIGHT(%s,%d)", expression, length);
	}

	public String subString(String expression, int start, int length) {
		return String.format("SUBSTRING(%s, %d, %d)", expression, start, length);
	}

	public String join(List<String> expression) {
		return String.join(" + ", expression);
	}

	public String mod(String expression1, String expression2 ) {
		return "(" + expression1 + " % " + expression2 + ")";
	}

	@Override
	public DataType parse(String type, Integer... length) {
		if(type == null)
			throw new IllegalArgumentException();

		if (type.equals("BIT")) {
			return DataType.BOOL;
		}
		else if ((type.equals("DECIMAL") || type.equals("NUMERIC")) && length[0] == 1 && (length.length <= 1 || length[1] == 0)) {
			return DataType.BOOL;
		}
		else if(type.equals("INT")) {
			return DataType.INT;
		}
		else if((type.equals("DECIMAL") || type.equals("NUMERIC")) && length.length == 1) {
			return DataType.INT;
		}
		else if((type.equals("DECIMAL") || type.equals("NUMERIC")) && length.length == 2) {
			return DataType.REAL;
		}
		else if(type.equals("NVARCHAR")) {
			return DataType.NVARCHAR;
		}
		else if(type.equals("NCHAR")) {
			return DataType.NCHAR;
		}
		else if(type.equals("VARCHAR")) {
			return DataType.VARCHAR;
		}
		else if(type.equals("CHAR")) {
			return DataType.CHAR;
		}
		else if(type.equals("DATE")) {
			return DataType.DATE;
		}
		else if(type.equals("DATETIME") || type.equals("DATETIME2")) {
			return DataType.DATETIME;
		}

		throw new IllegalArgumentException("[" + type + "] is undefined.");
	}

	@Override
	public String collate() {
		return "COLLATE " + collateType;
	}

	@Override
	public String tableCommentDdl(String tableName, String comment) {
		if(comment == null || comment.isEmpty()) return "";

		return "EXEC sys.sp_addextendedproperty "
				+ " @name=N'MS_Description'\r\n"
				+ ",@value=N'" + comment + "'\r\n"
				+ ",@level0type=N'SCHEMA'\r\n"
				+ ",@level0name=N'dbo'\r\n"
				+ ",@level1type=N'TABLE'\r\n"
				+ ",@level1name=N'" + tableName + "';\r\n";
	}

	@Override
	public String columnCommentDdl(String tableName, String columnName, String comment) {
		if(comment == null || comment.isEmpty()) return "";

		return "EXEC sys.sp_addextendedproperty "
				+ " @name=N'MS_Description'\r\n"
				+ ",@value=N'" + comment + "'\r\n"
				+ ",@level0type=N'SCHEMA'\r\n"
				+ ",@level0name=N'dbo'\r\n"
				+ ",@level1type=N'TABLE'\r\n"
				+ ",@level1name=N'" + tableName + "'\r\n"
				+ ",@level2type=N'COLUMN'\r\n"
				+ ",@level2name=N'" + columnName + "';\r\n";
	}

	@Override
	public String rlsDdl(String tableName) {
		return "";
	}

}
