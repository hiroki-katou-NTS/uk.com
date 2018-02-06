/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.worktime.worktimeset;

/**
 * The Interface WorkTimeSettingPub.
 */
public interface WorkTimeSettingPub {

	/**
	 * Checks if is flow work.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @return true, if is flow work
	 */
	//流動勤務かどうかの判断処理
	boolean isFlowWork(String companyId, String worktimeCode);
	
}
