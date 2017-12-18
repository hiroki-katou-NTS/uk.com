/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface WorkTimeSettingService.
 */
public interface WorkTimeSettingService {

	/**
	 * Gets the holiday work schedule.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @param workTypeCode the work type code
	 * @return the holiday work schedule
	 */

	/**
	 * Rounding time.
	 *
	 * @param time the time
	 * @return the time with day attr
	 */
	// 出勤系時刻を丸める
	TimeWithDayAttr roundingTime(TimeWithDayAttr time);

	/**
	 * Gets the predetemine time setting.
	 *
	 * @param companyId the company id
	 * @param workTypeCode the work type code
	 * @param workNo the work no
	 * @return the predetemine time setting
	 */
	//	所定時間帯を取得す�
	PredetemineTimeSetting getPredetemineTimeSetting(String companyId, WorkTypeCode workTypeCode, WorkNo workNo);

}
