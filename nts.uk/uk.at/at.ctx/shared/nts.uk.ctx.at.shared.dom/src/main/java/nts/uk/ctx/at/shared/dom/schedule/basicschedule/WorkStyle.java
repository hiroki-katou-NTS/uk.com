package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

 /**
  * The Enum WorkStyle.
  */
 // 出勤休日区分
public enum WorkStyle {
	
	/**
	 *  1日休日系
	 */
	ONE_DAY_REST(0),

	/**
	 *  午前出勤系
	 */
	MORNING_WORK(1),

	/**
	 *  午後出勤系
	 */
	AFTERNOON_WORK(2),

	/**
	 *  1日出勤系
	 */
	ONE_DAY_WORK(3);

	public int value;

	private WorkStyle(int value) {
		this.value = value;
	}
}
