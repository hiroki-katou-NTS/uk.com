package nts.uk.ctx.exio.dom.exo.datafomat;

public enum ItemType {
	
	//数値型
	NUMERIC(0, "Enum_ItemType_NUMERIC"),
	//文字型
	CHARACTER(1, "Enum_ItemType_CHARACTER"),
	//日付型
	DATE(2, "Enum_ItemType_DATE"),
	//時刻型
	INS_TIME(3, "Enum_ItemType_INS_TIME"),
	//時刻型
	TIME(4, "Enum_ItemType_TIME"),
	//在職区分
	ATWORK(5, "Enum_ItemType_ATWORK");
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ItemType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
