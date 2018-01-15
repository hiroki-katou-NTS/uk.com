/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

/**
 * The Interface WorkTimeSettingPolicy.
 */
public interface WorkTimeSettingPolicy {

	/**
	 * Can register.
	 *
	 * @param wtSet the wt set
	 * @return true, if successful
	 */
	boolean canRegister(WorkTimeSetting wtSet);
}
