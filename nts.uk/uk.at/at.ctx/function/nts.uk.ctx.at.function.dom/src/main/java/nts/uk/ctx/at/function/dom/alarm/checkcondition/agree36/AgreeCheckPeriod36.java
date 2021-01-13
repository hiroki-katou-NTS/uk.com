package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

/**
 * The enum 36 Agreement check period.<br>
 * Enum 36協定チェック
 *
 * @author nws-minhnb
 */
public enum AgreeCheckPeriod36 {

	/**
	 * 1・2・4週間
	 */
	ONE_TWO_FOUR_WEEKS("1・2・4週間"),

	/**
	 * 1ヶ月
	 */
	ONE_MONTH("1ヶ月"),

	/**
	 * 2ヶ月
	 */
	TWO_MONTHS("2ヶ月"),

	/**
	 * 3ヶ月
	 */
	THREE_MONTHS("3ヶ月"),

	/**
	 * 年間
	 */
	YEAR("年間"),

	/**
	 * 複数月平均
	 */
	MULTI_MONTH_AVERAGE("複数月平均");

	public final String value;

	private AgreeCheckPeriod36(String value) {
		this.value = value;
	}

}
