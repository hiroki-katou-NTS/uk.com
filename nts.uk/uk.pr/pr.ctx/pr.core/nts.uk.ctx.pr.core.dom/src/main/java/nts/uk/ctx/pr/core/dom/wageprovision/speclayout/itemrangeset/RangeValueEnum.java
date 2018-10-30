package nts.uk.ctx.pr.core.dom.wageprovision.speclayout.itemrangeset;

/**
 * 範囲値の属性
 */
public enum RangeValueEnum {
	AMOUNT_OF_MONEY(0, "Enum_Range_Value_AMOUNT_OF_MONEY"),
	TIME(1, "Enum_Range_Value_TIME"),
	TIMES(2, "Enum_Range_Value_TIMES");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private RangeValueEnum(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
