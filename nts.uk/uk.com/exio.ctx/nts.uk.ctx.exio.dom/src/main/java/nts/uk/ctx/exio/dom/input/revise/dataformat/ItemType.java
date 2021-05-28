package nts.uk.ctx.exio.dom.input.revise.dataformat;

/**
 * 
 * @author HungTT 項目型
 *
 */
public enum ItemType {
	/**
	 * 0: 文字型
	 */
	CHARACTER(0, "Enum_ItemType_CHARACTER"),
	/**
	 * 1: 数値型
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
	 * 4: 時間
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
}
