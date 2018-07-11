package nts.uk.ctx.exio.dom.exo.categoryitemdata;

public enum SpecialItem {
	
	//特殊項目ではない
	NOT_SPECIAL(0, "Enum_SpecialItem_NOT_SPECIAL"),
	//日付型
	DATE_TIME(1, "Enum_SpecialItem_DATE_TIME");
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SpecialItem(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
