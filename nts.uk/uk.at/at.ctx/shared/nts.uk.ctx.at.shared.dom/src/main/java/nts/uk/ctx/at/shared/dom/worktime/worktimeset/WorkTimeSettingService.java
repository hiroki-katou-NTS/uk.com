/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;

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
	// 打刻反映時間帯を取得する
	List<StampReflectTimezone> getStampReflectTimezone(String companyId, String workTimeCode, Integer start1,
			Integer start2, Integer end1, Integer end2);

}
