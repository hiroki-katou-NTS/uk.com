package nts.uk.ctx.at.shared.dom.worktype.service;

import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAtr;

/**
 * The Interface WorktypeService.
 */
public interface WorktypeService {
	
	/**
	 * Gets the attendance holiday atr.
	 *
	 * @param worktypeCode the worktype code
	 * @return the attendance holiday atr
	 */
	// 1日半日出勤・1日休日系の判定
	public AttendanceHolidayAtr getAttendanceHolidayAtr(String worktypeCode);

}
