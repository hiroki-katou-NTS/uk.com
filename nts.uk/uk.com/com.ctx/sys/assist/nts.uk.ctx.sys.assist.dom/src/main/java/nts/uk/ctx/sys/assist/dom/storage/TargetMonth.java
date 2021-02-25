package nts.uk.ctx.sys.assist.dom.storage;

/**
 * 対象月
 */
public enum TargetMonth {
	/**
	 * 当月
	 */
	CURRENT_MONTH(1, "Enum_TargetMonth_CURRENT_MONTH"),
	/**
	 * １ヶ月
	 */
	ONE_MONTH_AGO(2, "Enum_TargetMonth_ONE_MONTH_AGO"),
	/**
	 * 2ヶ月
	 */
	TWO_MONTH_AGO(3, "Enum_TargetMonth_TWO_MONTH_AGO"),
	/**
	 * 3ヶ月
	 */
	THREE_MONTH_AGO(4, "Enum_TargetMonth_THREE_MONTH_AGO"),
	/**
	 * 4ヶ月
	 */
	FOUR_MONTH_AGO(5, "Enum_TargetMonth_FOUR_MONTH_AGO"),
	/**
	 * 5ヶ月
	 */
	FIVE_MONTH_AGO(6, "Enum_TargetMonth_FIVE_MONTH_AGO"),
	/**
	 * 6ヶ月
	 */
	SIX_MONTH_AGO(7, "Enum_TargetMonth_SIX_MONTH_AGO"),
	/**
	 * 7ヶ月
	 */
	SEVEN_MONTH_AGO(8, "Enum_TargetMonth_SEVEN_MONTH_AGO"),
	/**
	 * 8ヶ月
	 */
	EIGHT_MONTH_AGO(9, "Enum_TargetMonth_EIGHT_MONTH_AGO"),
	/**
	 * 9ヶ月
	 */
	NINE_MONTH_AGO(10, "Enum_TargetMonth_NINE_MONTH_AGO"),
	/**
	 * 10ヶ月
	 */
	TEN_MONTH_AGO(11, "Enum_TargetMonth_TEN_MONTH_AGO"),
	/**
	 * 11ヶ月
	 */
	ELEVEN_MONTH_AGO(12, "Enum_TargetMonth_ELEVEN_MONTH_AGO");
	
	public final int value;
	public final String nameId;
	
	private TargetMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
