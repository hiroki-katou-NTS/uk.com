package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

/**
 * 出勤休日区分
 * @author keisuke_hoshina
 */
@AllArgsConstructor
public enum AttendanceHolidayAttr {

	/** １日出勤系 */
	FULL_TIME(3, "１日出勤系"),
	
	/** 午前出勤系 */
	MORNING(1, "午前出勤系"),
	
	/** 午後出勤系 */
	AFTERNOON(2, "午後出勤系"),
	
	/** １日休日系 */
	HOLIDAY(0, "１日休日系");
	
	public final int value;
	
	public final String name;
	
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
	
	/**
	 * decision holiday
	 * @return is holiday
	 */
	public boolean isHoliday() {
		return this.equals(HOLIDAY);
	}
	
	/**
	 * 1日出勤系か判定する
	 * @return　1日出勤である
	 */
	public boolean isFullTime() {
		return this.equals(FULL_TIME);
	}
	
	/**
	 * 午前出勤系か判定する
	 * @return　1日出勤である
	 */
	public boolean isMorning() {
		return this.equals(MORNING);
	}
	
	/**
	 * 午後出勤系か判定する
	 * @return　1日出勤である
	 */
	public boolean isAfternoon() {
		return this.equals(AFTERNOON);
	}
}
