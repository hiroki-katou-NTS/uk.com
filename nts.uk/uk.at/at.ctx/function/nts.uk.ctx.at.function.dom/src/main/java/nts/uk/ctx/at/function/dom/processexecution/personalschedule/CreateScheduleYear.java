package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

/**
 * The enum Create schedule year.<br>
 * Enum 更新処理スケジュール作成指定年
 *
 * @author tutk
 */
public enum CreateScheduleYear {

	/**
	 * 本年
	 */
	THIS_YEAR(0, "本年"),

	/**
	 * 翌年
	 */
	FOLLOWING_YEAR(1, "翌年");

	/**
	 * The Value.
	 */
	public final int value;
	/**
	 * The Name id.
	 */
	public final String nameId;

	/**
	 * Instantiates a new Create schedule year.
	 *
	 * @param value  the value
	 * @param nameId the name id
	 */
	private CreateScheduleYear(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
