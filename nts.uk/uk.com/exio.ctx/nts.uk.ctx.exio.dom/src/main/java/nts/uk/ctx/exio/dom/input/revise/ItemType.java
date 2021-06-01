package nts.uk.ctx.exio.dom.input.revise;

import nts.uk.ctx.exio.dom.input.DataType;

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
	TIME(4, "Enum_ItemType_TIME"),
	/**
	 * 5: 時刻型
	 */
	INS_TIME(5, "Enum_ItemType_INS_TIME");

	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ItemType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	public DataType getDataType() {
		switch(this) {
		case INT:
		case TIME:
		case INS_TIME:
			return DataType.INT;
		case STRING:
			return DataType.STRING;
		case REAL:
			return DataType.REAL;
		case DATE:
			return DataType.DATE;
		default:
			throw new RuntimeException("システム上使用できない方が指定されました。");
		}
	}
}
