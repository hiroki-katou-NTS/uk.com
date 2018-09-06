package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

/**
 * 項目名表示
 */
public enum ItemNameDisplayEnum {
	NOT_SHOW(0, "Enum_Item_Name_Display_NOT_USE"), 
	SHOW(1, "Enum_Item_Name_Display_USE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ItemNameDisplayEnum(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
