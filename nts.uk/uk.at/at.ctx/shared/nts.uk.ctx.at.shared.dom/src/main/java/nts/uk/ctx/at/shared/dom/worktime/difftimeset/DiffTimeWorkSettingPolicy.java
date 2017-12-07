/******************************************************************

 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * The Interface DiffTimezoneSettingPolicy.
 */
public interface DiffTimeWorkSettingPolicy {
	
	/**
	 * Can register.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @param diffTimeWorkSetting the diff time work setting
	 * @return true, if successful
	 */
	public boolean canRegister(String companyId,WorkTimeCode worktimeCode,DiffTimeWorkSetting diffTimeWorkSetting);

}
