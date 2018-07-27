/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.worktime.predset;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Interface PredetemineTimeSettingPub.
 */
public interface PredetemineTimeSettingPub {
	
	/**
	 * Checks if is working twice.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @return true, if is working twice
	 */
	// 2回勤務かどうかの判断処理
	boolean IsWorkingTwice(String companyId, String workTimeCode);
	
	/**
	 * Checks if is working twice with list
	 * @param companyId
	 * @param workTimeCodes
	 * @return
	 */
	Map<String, Boolean> checkWorkingTwice(String companyId, List<String> workTimeCodes);

	/**
	 * Acquire predetermined time.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 * @return the optional
	 */
	//所定時間（1日の時間内訳）を取得する
	Optional<PredeterminedTimeExport> acquirePredeterminedTime(String companyId, String workTimeCode);
}
