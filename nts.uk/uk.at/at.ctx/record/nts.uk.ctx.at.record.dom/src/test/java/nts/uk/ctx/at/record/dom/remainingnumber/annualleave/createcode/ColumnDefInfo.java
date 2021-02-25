package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import lombok.Getter;
import lombok.Setter;

/**
 * カラム定義
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class ColumnDefInfo {

	/**
	 * カラムNo
	 */
	private String columnNo = "";

	/**
	 * カラム名
	 */
	private String columnName = "";

	/**
	 * カラムコメント
	 */
	private String columnNameComment = "";

	/**
	 * PK
	 */
	private String pk = "";

	/**
	 *　型
	 */
	private String columnType = "";

	/**
	 *　列長
	 */
	private String columnLength = "";

	/**
	 *　小数
	 */
	private String decimalLength = "";

	/**
	 *　データ長
	 */
	private String dataLength = "";

	/**
	 *　デフォルト値
	 */
	private String defaultValue = "";

	/**
	 *　Nullable
	 */
	private String nullable = "";

	/**
	 *　備考
	 */
	private String biko = "";

	/**
	 * デバッグ用
	 * @return
	 */
	public String debugString() {

		StringBuilder sb = new StringBuilder();

		sb.append("columnNo='");
		sb.append(columnNo);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("columnName='");
		sb.append(columnName);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("columnNameComment='");
		sb.append(columnNameComment);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("pk='");
		sb.append(pk);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("columnType='");
		sb.append(columnType);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("columnLength='");
		sb.append(columnLength);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("decimalLength='");
		sb.append(decimalLength);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("dataLength='");
		sb.append(dataLength);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("defaultValue='");
		sb.append(defaultValue);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("nullable='");
		sb.append(nullable);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("defaultValue='");
		sb.append(defaultValue);
		sb.append("'");
		sb.append(System.lineSeparator());

		return sb.toString();
	}

}
