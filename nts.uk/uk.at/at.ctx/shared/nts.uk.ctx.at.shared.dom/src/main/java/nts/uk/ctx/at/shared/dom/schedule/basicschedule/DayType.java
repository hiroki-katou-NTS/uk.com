package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

/**
 * 
 * @author sonnh1
 *
 */
public enum DayType {
	// 年休
	ANNUAL_HOLIDAY(0),

	// 積立年休
	YEARLY_RESERVED(1),

	// 代休
	SUBSTITUTE_HOLIDAY(2),
	
	// 欠勤
	ABSENCE(3),
	
	// 特別休暇
	SPECIAL_HOLIDAY(4),

	// 時間消化休暇
	TIME_DIGEST_VACATION(5);

	public int value;

	private DayType(int value) {
		this.value = value;
	}
}
