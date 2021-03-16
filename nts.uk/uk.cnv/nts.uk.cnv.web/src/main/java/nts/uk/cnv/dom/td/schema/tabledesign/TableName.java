package nts.uk.cnv.dom.td.schema.tabledesign;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;

/**
 * テーブル名
 * 責務：
 * テーブル名の命名に関するルールを担保する
 */
public class TableName {
	private String value;

	private final String regex = "[A-Z]{3}[MDCRT]T_[0-9|A-Z|_]+";

	public TableName(String name) {
		this.value = name;
	}

	public void validate(String name) {
		if(name.length() > 50) {
			throw new BusinessException(new RawErrorMessage("テーブル名の桁数は半角50字以内で入力してください。長さ：" + name.length()));
		}

		if(!name.matches(regex)) {
			throw new BusinessException(new RawErrorMessage(name + "はテーブル名の命名規約違反です。"));
		}

		// TODO: 他のチェック

		return;
	}

	public String v() {
		return this.value;
	}

	public String pkName() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.value);
		sb.setCharAt(4, 'P');

		return sb.toString();
	}

	public String ukName(String suffix) {
		StringBuilder sb = new StringBuilder();
		sb.append(this.value);
		sb.setCharAt(4, 'U');

		return sb.toString() + suffix;
	}

	public String indexName(String suffix) {
		StringBuilder sb = new StringBuilder();
		sb.append(this.value);
		sb.setCharAt(4, 'I');

		return sb.toString() + suffix;
	}
}
