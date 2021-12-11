package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author HungTT 項目型
 *
 */
public enum ItemType {
	/**
	 * 0: 数値型
	 */
	NUMERIC(0, "Enum_ItemType_NUMERIC"),
	/**
	 * 1: 文字型
	 */
	CHARACTER(1, "Enum_ItemType_CHARACTER"),
	/**
	 * 2: 日付型
	 */
	DATE(2, "Enum_ItemType_DATE"),
	/**
	 * 3: 時刻型
	 */
	INS_TIME(3, "Enum_ItemType_INS_TIME"),
	/**
	 * 4: 時間
	 */
	TIME(4, "Enum_ItemType_TIME");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ItemType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
