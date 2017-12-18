/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.List;

/**
 * The Interface PredetemineTimeSettingRepository.
 */
public interface PredetemineTimeSettingRepository {

	/**
	 * Find by work time code.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 * @return the predetemine time setting
	 */
	public PredetemineTimeSetting findByWorkTimeCode(String companyId, String workTimeCode);

	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void save(PredetemineTimeSetting domain);

	/**
	 * Find by start.
	 *
	 * @param companyID the company ID
	 * @param workTimeCodes the sift C ds
	 * @param startClock the start clock
	 * @return the list
	 */
	public List<PredetemineTimeSetting> findByStart(String companyID, List<String> workTimeCodes, int startClock);

	/**
	 * Find by end.
	 *
	 * @param companyID the company ID
	 * @param workTimeCodes the sift C ds
	 * @param endClock the end clock
	 * @return the list
	 */
	public List<PredetemineTimeSetting> findByEnd(String companyID, List<String> workTimeCodes, int endClock);

	/**
	 * Find by start and end.
	 *
	 * @param companyID the company ID
	 * @param workTimeCodes the sift C ds
	 * @param startClock the start clock
	 * @param endClock the end clock
	 * @return the list
	 */
	public List<PredetemineTimeSetting> findByStartAndEnd(String companyID, List<String> workTimeCodes, int startClock, int endClock);
}
