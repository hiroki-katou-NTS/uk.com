package nts.uk.ctx.at.shared.dom.worktype;

/**
 * 出勤休日区分
 * @author keisuke_hoshina
 */
public enum AttendanceHolidayAttr {

	/** １日出勤系 */
	FULL_TIME,
	
	/** 午前出勤系 */
	MORNING,
	
	/** 午後出勤系 */
	AFTERNOON,
	
	/** １日休日系 */
	HOLIDAY,
	;
	public boolean isHalfDayWorking() {
		switch (this) {
		case FULL_TIME:
		case HOLIDAY:
			return false;
		case MORNING:
		case AFTERNOON:
			return true;
		default:
			throw new RuntimeException("invalid value:" + this);
		}
	}
}
