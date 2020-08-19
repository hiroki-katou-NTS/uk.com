package nts.uk.cnv.dom.databasetype;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SqlServerSpec implements DataBaseSpec{

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

}
