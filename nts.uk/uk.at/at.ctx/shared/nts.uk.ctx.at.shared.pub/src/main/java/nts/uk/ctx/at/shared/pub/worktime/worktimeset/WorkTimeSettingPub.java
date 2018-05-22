/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.worktime.worktimeset;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * check if is flow work with list
	 * @param companyId
	 * @param worktimeCode
	 * @return
	 */
	Map<String, Boolean> checkFlowWork(String companyId, List<String> worktimeCodes);
	
	/**
	 * Gets the work time setting name.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @return the work time setting name
	 */
	//就業時間帯名称を取得する
	String getWorkTimeSettingName(String companyId, String worktimeCode);
	/**
	 * check is exist
	 * @param companyId
	 * @param worktimeCode
	 * @return true if is exist
	 */
	boolean isExist(String companyId, String worktimeCode);
	
}
