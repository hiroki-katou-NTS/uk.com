/******************************************************************

 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet;

/**
 * The Interface DiffTimezoneSettingPolicy.
 */
public interface DiffTimeWorkSettingPolicy {
	
	/**
	 * Can register.
	 *
	 * @param pred the pred
	 * @param diffTimeWorkSetting the diff time work setting
	 * @return true, if successful
	 */
	public boolean canRegister(PredetemineTimeSet pred,DiffTimeWorkSetting diffTimeWorkSetting);

}
