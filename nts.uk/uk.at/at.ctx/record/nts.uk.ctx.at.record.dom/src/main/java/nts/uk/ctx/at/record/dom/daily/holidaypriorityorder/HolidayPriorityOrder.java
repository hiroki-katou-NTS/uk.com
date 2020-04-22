package nts.uk.ctx.at.record.dom.daily.holidaypriorityorder;


public enum HolidayPriorityOrder {

	/** 代休 */
	SUB_HOLIDAY(0),

	/** 60H超休 */
	SIXTYHOUR_HOLIDAY(1),

	/** 特別休暇 */
	SPECIAL_HOLIDAY(2),

	/** 年休 */
	ANNUAL_HOLIDAY(3);
	
	public int value;

	private HolidayPriorityOrder(int value) {
		this.value = value;
	}
}
