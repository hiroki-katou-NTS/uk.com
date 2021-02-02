package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;
/**
 * 開始月からの抽出期間
 * @author phongtq
 *
 */
public enum ExtractFromStartMonth {

	/** 1ヶ月前 */
	ONE_MONTH(1, "1ヶ月前"),

	/** 2ヶ月前 */
	TWO_MONTH(2, "2ヶ月前"),

	/** 3ヶ月前 */
	THREE_MONTH(3, "3ヶ月前"),

	/** 4ヶ月前 */
	FOUR_MONTH(4, "4ヶ月前"),

	/** 5ヶ月前 */
	FIVE_MONTH(5, "5ヶ月前"),

	/** 6ヶ月前 */
	SIX_MONTH(6, "6ヶ月前"),

	/** 7ヶ月前 */
	SEVEN_MONTH(7, "7ヶ月前"),

	/** 8ヶ月前 */
	EIGHT_MONTH(8, "8ヶ月前"),

	/** 9ヶ月前 */
	NINE_MONTH(9, "9ヶ月前"),

	/** 10ヶ月前 */
	TEN_MONTH(10, "10ヶ月前"),

	/** 11ヶ月前 */
	ELEVEN_MONTH(11, "11ヶ月前"),
	
	/** 12ヶ月前 */
	TWELVE_MONTH(12, "12ヶ月前");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ExtractFromStartMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
