package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder;


/**
 * 時間休暇相殺優先順位の項目
 * @author daiki_ichioka
 */
public enum HolidayPriorityOrder {

	/** 代休 */
	SUB_HOLIDAY(0),

	/** 60H超休 */
	SIXTYHOUR_HOLIDAY(1),

	/** 特別休暇 */
	SPECIAL_HOLIDAY(2),

	/** 年休 */
	ANNUAL_HOLIDAY(3),

	/** 子の看護 */
	CHILD_CARE(4),

	/** 介護 */
	CARE(5);
	
	public final int value;

	private HolidayPriorityOrder(int value) {
		this.value = value;
	}
}
