package nts.uk.ctx.sys.assist.dom.tablelist;

/**
 * 参照月
 * 
 * @author thanh.tq
 *
 */
public enum ReferenceMonth {
	//当月
	THIS_MONTH(0, "Enum_ReferenceMonth_THIS_MONTH"),
	//1ヵ月前
	ONE_MONTH_AGO(1, "Enum_ReferenceMonth_ONE_MONTH_AGO"),
	//2ヵ月前
	TWO_MONTH_AGO(2, "Enum_ReferenceMonth_TWO_MONTH_AGO"),
	//3ヵ月前
	THREE_MONTH_AGO(3, "Enum_ReferenceMonth_THREE_MONTH_AGO"),
	//4ヵ月前
	FOUR_MONTH_AGO(4, "Enum_ReferenceMonth_FOUR_MONTH_AGO"),
	//5ヵ月前
	FIVE_MONTH_AGO(5, "Enum_ReferenceMonth_FIVE_MONTH_AGO"),
	//6ヵ月前
	SIX_MONTH_AGO(6, "Enum_ReferenceMonth_SIX_MONTH_AGO"),
	//7ヵ月前
	SEVEN_MONTH_AGO(7, "Enum_ReferenceMonth_SEVEN_MONTH_AGO"),
	//8ヵ月前
	EIGHT_MONTH_AGO(8, "Enum_ReferenceMonth_EIGHT_MONTH_AGO"),
	//9ヵ月前
	NINE_MONTH_AGO(9, "Enum_ReferenceMonth_NINE_MONTH_AGO"),
	//10ヵ月前
	TEN_MONTH_AGO(10, "Enum_ReferenceMonth_TEN_MONTH_AGO"),
	//11ヵ月前
	ELEVEN_MOTH_AGO(11, "Enum_ReferenceMonth_ELEVEN_MOTH_AGO");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ReferenceMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
