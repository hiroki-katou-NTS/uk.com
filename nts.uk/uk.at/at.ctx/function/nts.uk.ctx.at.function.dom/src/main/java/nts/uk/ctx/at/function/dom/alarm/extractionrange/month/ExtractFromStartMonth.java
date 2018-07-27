package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;
/**
 * 開始月からの抽出期間
 * @author phongtq
 *
 */
public enum ExtractFromStartMonth {

	/** 1ヶ月前 */
	ONEMONTH(1, "1ヶ月前"),

	/** 2ヶ月前 */
	TWOMONTH(2, "2ヶ月前"),

	/** 3ヶ月前 */
	THREEMONTH(3, "3ヶ月前"),

	/** 4ヶ月前 */
	FOURMONTH(4, "4ヶ月前"),

	/** 5ヶ月前 */
	FIVEMONTH(5, "5ヶ月前"),

	/** 6ヶ月前 */
	SIXMONTH(6, "6ヶ月前"),

	/** 7ヶ月前 */
	SEVENMONTH(7, "7ヶ月前"),

	/** 8ヶ月前 */
	EIGHTMONTH(8, "8ヶ月前"),

	/** 9ヶ月前 */
	NINEMONTH(9, "9ヶ月前"),

	/** 10ヶ月前 */
	TENMONTH(10, "10ヶ月前"),

	/** 11ヶ月前 */
	ELEVENMONTH(11, "11ヶ月前"),
	
	/** 12ヶ月前 */
	TWELVEMONTH(12, "12ヶ月前");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ExtractFromStartMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
