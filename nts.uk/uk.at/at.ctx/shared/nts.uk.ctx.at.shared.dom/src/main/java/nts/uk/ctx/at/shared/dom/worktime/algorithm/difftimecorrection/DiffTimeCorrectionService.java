/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.algorithm.difftimecorrection;

import nts.uk.ctx.at.shared.dom.schedule.basicschedule.JoggingWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface DiffTimeCorrectionService.
 */
public interface DiffTimeCorrectionService {

	/**
	 * Correction.
	 *
	 * @param difftime the difftime
	 * @param difftimeSetting the difftime setting
	 * @param predTime the pred time
	 */
	void correction(JoggingWorkTime difftime,DiffTimeWorkSetting difftimeSetting,PredetemineTimeSetting predTime);
	
	/**
	 * Work time update.
	 *
	 * @param difftime the difftime
	 * @param difftimeSetting the difftime setting
	 * @param predTime the pred time
	 */
	void workTimeUpdate(JoggingWorkTime difftime, DiffTimeWorkSetting difftimeSetting,PredetemineTimeSetting predTime);
	
	/**
	 * Status atr.
	 *
	 * @param compare the compare
	 * @param base the base
	 * @return the duplication status of time zone
	 */
	DuplicationStatusOfTimeZone statusAtr(TimeSpanForCalc compare,TimeSpanForCalc base);
	
	/**
	 * Shift time.
	 *
	 * @param difftime the difftime
	 * @param start the start
	 * @param end the end
	 * @param predTime the pred time
	 * @return the time span for calc
	 */
	TimeSpanForCalc shiftTime(JoggingWorkTime difftime, TimeWithDayAttr start, TimeWithDayAttr end,PredetemineTimeSetting predTime);
	
	/**
	 * One daycheck.
	 *
	 * @param difftime the difftime
	 * @param start the start
	 * @param end the end
	 * @param startLast the start last
	 * @param endLast the end last
	 * @param predTime the pred time
	 * @return the time span for calc
	 */
	TimeSpanForCalc oneDaycheck(JoggingWorkTime difftime, TimeWithDayAttr start, TimeWithDayAttr end,TimeWithDayAttr startLast, TimeWithDayAttr endLast,
			PredetemineTimeSetting predTime);
}
