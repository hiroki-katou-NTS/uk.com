package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

/**
 * The enum Target month.<br>
 * Enum 更新処理対象月
 *
 * @author nws-minhnb
 */
public enum TargetMonth {

	/**
	 * システム日付の月
	 */
	CURRENT_MONTH(0, "システム日付の月"),

	/**
	 * システム日付の翌月
	 */
	MONTH_LATER(1, "システム日付の翌月"),

	/**
	 * システム日付の翌々月
	 */
	TWO_MONTH_LATER(2, "システム日付の翌々月"),

	/**
	 * 開始月を指定する
	 */
	DESIGNATE_START_MONTH(3, "開始月を指定する");

	/**
	 * The value.
	 */
	public final int value;

	/**
	 * The name id.
	 */
	public final String nameId;

	/**
	 * Instantiates a new <code>TargetMonth</code>.
	 *
	 * @param value  the value
	 * @param nameId the name id
	 */
	private TargetMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
