package nts.uk.ctx.exio.dom.input;

/**
 * 
 * データ型
 *
 */
public enum DataType {
	/**
	 * 0: 文字
	 */
	STRING(0, "Enum_DataType_STRING"),
	/**
	 * 1: 整数
	 */
	INT(1, "Enum_DataType_INT"),
	/**
	 * 2: 実数
	 */
	REAL(2, "Enum_DataType_REAL"),
	/**
	 * 3: 日付
	 */
	DATE(3, "Enum_DataType_DATE"),
	
	;

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private DataType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
