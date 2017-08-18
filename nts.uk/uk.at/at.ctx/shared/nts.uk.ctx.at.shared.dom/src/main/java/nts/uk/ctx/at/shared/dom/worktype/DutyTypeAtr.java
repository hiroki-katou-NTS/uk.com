package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DutyTypeAtr {
	/* 出勤 */
	ATTENDANCE(0),
	/* 休日  */
	HOLIDAY(1),
	/* 年休 */
	ANNUAL_HOLIDAY(2),
	/* 休日出勤  */
	HOLIDAY_WORK(3),
	/* 積立年休  */
	YEARLY_RESERVED(4),
	/* 特別休暇  */
	SPECIAL_HOLIDAY(5),
	/* 欠勤 */
	ABSENCE(6),
	/* 代休  */
	SUBSTITUTE_HOLIDAY(7),
	/* 振出 */
	SHOOTING(8),
	/* 振休 */
	PAUSE(9),
	/* 時間消化休暇  */
	TIME_DIGEST(10),
	/* 連続勤務 */
	CONTINUOUS_WORK(11),
	/* 休暇  */
	LEAVE_OF_ABSENCE(12),
	/* 休業 */		
	CLOSURE(13);
	
	public final int value;

}
