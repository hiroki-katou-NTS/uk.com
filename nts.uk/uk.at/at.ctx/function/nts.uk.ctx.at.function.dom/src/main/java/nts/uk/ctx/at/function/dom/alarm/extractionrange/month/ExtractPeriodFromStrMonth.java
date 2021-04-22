package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

/**
 * 開始月からの抽出期間 (E4_5-KAL004-パターン設定)
 *
 */
public enum ExtractPeriodFromStrMonth {
	/** 1ヶ月間 */
	ONE_MONTH(1, "1ヶ月間"),

	/** 2ヶ月間 */
	TWO_MONTH(2, "2ヶ月間"),

	/** 3ヶ月間 */
	THREE_MONTH(3, "3ヶ月間"),

	/** 4ヶ月間 */
	FOUR_MONTH(4, "4ヶ月間"),

	/** 5ヶ月間 */
	FIVE_MONTH(5, "5ヶ月間"),

	/** 6ヶ月間 */
	SIX_MONTH(6, "6ヶ月間"),

	/** 7ヶ月間 */
	SEVEN_MONTH(7, "7ヶ月間"),

	/** 8ヶ月間 */
	EIGHT_MONTH(8, "8ヶ月間"),

	/** 9ヶ月間 */
	NINE_MONTH(9, "9ヶ月間"),

	/** 10ヶ月間 */
	TEN_MONTH(10, "10ヶ月間"),

	/** 11ヶ月間 */
	ELEVEN_MONTH(11, "11ヶ月間"),
	
	/** 12ヶ月間 */
	TWELVE_MONTH(12, "12ヶ月間");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ExtractPeriodFromStrMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
