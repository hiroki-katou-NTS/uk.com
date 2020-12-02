package nts.uk.cnv.dom.tabledefinetype.databasetype;

import java.util.List;

import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.tabledefinetype.DataType;
import nts.uk.cnv.dom.tabledefinetype.DatabaseSpec;

@NoArgsConstructor
public class PostgresSpec implements DatabaseSpec{

	/** 照合順序 **/
    public static final String collateType = "default";

	public String dataType(DataType type, Integer... length) {
		switch (type) {
		case BOOL:
			return "NUMERIC(1)";
		case INT:
			return String.format("NUMERIC(%d)", (Object[]) length);
		case REAL:
			return String.format("NUMERIC(%d,%d)", (Object[]) length);
		case CHAR:
			return String.format("CHAR(%d)", (Object[]) length);
		case VARCHAR:
			return String.format("VARCHAR(%d)", (Object[]) length);
		case NCHAR:
			return String.format("CHAR(%d)", (Object[]) length);
		case NVARCHAR:
			return String.format("VARCHAR(%d)", (Object[]) length);
		case DATE:
			return "DATE";
		case DATETIME:
			return "TIMESTAMP";
		case DATETIMEMS:
			return "TIMESTAMP(3)";
		case GUID:
			return "CHAR(36)";
		default:
			break;
		}
		throw new IllegalArgumentException();
	}

	public String param(String expression) {
		return ":" + expression;
	}

	public String declaration(String pramName, DataType type, Integer... length) {
		return String.format("%s %s;", pramName, this.dataType(type, length));
	}

	public String initialization(String pramName, String value) {
		return String.format("%s := '%s';", pramName, value);
	}

	public String initialization(String pramName, int value) {
		return String.format("%s := %d;", pramName, value);
	}

	public String cast(String expression, DataType type, Integer... length) {
		return String.format("CAST(%s AS %s)", expression, this.dataType(type, length));
	}

	public String newUuid() {
		return "gen_random_uuid()";
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
		return String.format("SUBSTR(%s, %d, %d)", expression, start, length);
	}

	public String join(List<String> expression) {
		return String.join(" || ", expression);
	}

	public String mod(String expression1, String expression2 ) {
		return "(" + expression1 + " % " + expression2 + ")";
	}

	@Override
	public DataType parse(String type, Integer... length) {
		if(type == null)
			throw new IllegalArgumentException();

		if (type.equals("NUMERIC") && length[0] == 1) {
			return DataType.BOOL;
		}
		else if(type.equals("NUMERIC") && length.length == 1) {
			return DataType.INT;
		}
		else if(type.equals("NUMERIC") && length.length == 2) {
			return DataType.REAL;
		}
		else if(type.equals("VARCHAR")) {
			return DataType.NVARCHAR;
		}
		else if(type.equals("CHAR")) {
			return DataType.NCHAR;
		}
		else if(type.equals("DATE")) {
			return DataType.DATE;
		}
		else if(type.equals("TIMESTAMP")) {
			return DataType.DATETIME;
		}

		throw new IllegalArgumentException();
	}

	@Override
	public String collate() {
		return "COLLATE " + collateType;
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

		// 行レベルセキュリティの有効化
		String policyEnable =
				"ALTER TABLE " + tableName +" ENABLE ROW LEVEL SECURITY;\r\n";

		// 全権持ちユーザ(postgres)には全アクセスを許可
		String policyForAdmin =
				  "CREATE POLICY admin_select_employee ON " + tableName + "\r\n"
				+ "  FOR ALL\r\n"
				+ "  TO PUBLIC\r\n"
				+ "  USING (current_user = 'postgres');\r\n";

		// それ以外のユーザにはセッション変数のcontractCodeと一致する行のみ全権限を与える
		String policyForOthers =
				  "CREATE POLICY company_select_employee ON " + tableName+ "\r\n"
				+ "  FOR ALL\r\n"
				+ "  TO PUBLIC\r\n"
				+ "  USING (" + Constants.ContractCodeParamName + " = current_setting('app.contractCode', TRUE));\r\n";

		// 特定のロール(c0010,c0020)に対して特定のアクセス権限を許可
		String policyGrant =
				"GRANT SELECT, INSERT, UPDATE, DELETE on " + tableName + " TO " + Constants.rlsUserName + ";\r\n";

		return policyEnable + "\r\n"
				+ policyForAdmin + "\r\n"
				+ policyForOthers + "\r\n"
				+ policyGrant;
	}

}
