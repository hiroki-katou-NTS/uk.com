/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.List;
import java.util.Optional;

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
	public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode);

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	public void add(PredetemineTimeSetting domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	public void update(PredetemineTimeSetting domain);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 */
	public void remove(String companyId,String workTimeCode);
	
	/**
	 * Find by start.
	 *
	 * @param companyID the company ID
	 * @param workTimeCodes the work time codes
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
	 * @param workTimeCodes the work time codes
	 * @param startClock the start clock
	 * @param endClock the end clock
	 * @return the list
	 */
	public List<PredetemineTimeSetting> findByStartAndEnd(String companyID, List<String> workTimeCodes, int startClock, int endClock);
	
	/**
	 * Find by company ID.
	 *
	 * @param companyID the company ID
	 * @return the list
	 */
	public List<PredetemineTimeSetting> findByCompanyID(String companyID);

	/**
	 * Find by code list.
	 *
	 * @param companyID the company ID
	 * @param worktimeCodes the worktime codes
	 * @return the list
	 */
	public List<PredetemineTimeSetting> findByCodeList(String companyID, List<String> worktimeCodes);
}
