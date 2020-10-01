package nts.uk.cnv.dom.databasetype;

import java.util.List;

public interface DataBaseSpec extends DataTypeDefine {

	// 変数定義
	public String param(String expression);
	public String declaration(String pramName, DataType type, Integer... length);
	public String initialization(String pramName, String value);
	public String initialization(String pramName, int value);

	// 文字列操作関数
	public String concat(String expression1, String expression2);
	public String left(String expression, int length);
	public String right(String expression, int length);
	public String subString(String expression, int start, int length);
	public String join(List<String> expression);

	// 数学関数
	public String mod(String expression1, String expression2 );

	// 型変換
	public String cast(String expression, DataType type, Integer... length);

	public String newUuid();
}
