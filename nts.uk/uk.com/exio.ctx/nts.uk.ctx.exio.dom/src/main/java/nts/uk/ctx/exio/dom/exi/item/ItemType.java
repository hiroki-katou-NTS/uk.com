package nts.uk.ctx.exio.dom.exi.item;

/**
 * 
 * @author DatLH 項目型
 *
 */
public enum ItemType {
	NUMERIC_TYPE(0, "Enum_ItemType_NUMERIC_TYPE"),
	CHARACTER_TYPE(1, "Enum_ItemType_CHARACTER_TYPE"),
	DATE_TYPE(2, "Enum_ItemType_DATE_TYPE"),
	TIME_TYPE(3, "Enum_ItemType_TIME_TYPE");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ItemType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
