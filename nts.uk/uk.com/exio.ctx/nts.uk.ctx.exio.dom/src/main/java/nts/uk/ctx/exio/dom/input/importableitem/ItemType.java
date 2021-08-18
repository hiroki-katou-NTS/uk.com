package nts.uk.ctx.exio.dom.input.importableitem;

import java.math.BigDecimal;

import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;

/**
 * 
 * 項目型
 *
 */
public enum ItemType {
	/**
	 * 0: 文字型
	 */
	STRING(0, "Enum_ItemType_CHARACTER"),
	/**
	 * 1: 整数型
	 */
	INT(1, "Enum_ItemType_INT"),
	/**
	 * 2: 実数型
	 */
	REAL(2, "Enum_ItemType_REAL"),
	/**
	 * 3: 日付型
	 */
	DATE(3, "Enum_ItemType_DATE"),
	/**
	 * 4: 時間型
	 */
	TIME_DURATION(4, "Enum_ItemType_TIME_DURATION"),
	/**
	 * 5: 時刻型
	 */
	TIME_POINT(5, "Enum_ItemType_TIME_POINT");

	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ItemType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	// 文字列を項目方に対応する型に変換する
	public Object parse(String value) {
		switch(this) {
		case INT:
		case TIME_DURATION:
		case TIME_POINT:
			return Long.parseLong(value);
		case REAL:
			return new BigDecimal(value);
		case DATE:
			// TODO:デフォルトの日付形式が必要
			return GeneralDate.fromString(value, "yyyyMMdd");
		case STRING:
			return value;
		default:
			throw new RuntimeException("unknown: " + this);
		}
	}
	
	public String getResourceText() {
		return I18NText.getText(this.nameId);
	}
}
