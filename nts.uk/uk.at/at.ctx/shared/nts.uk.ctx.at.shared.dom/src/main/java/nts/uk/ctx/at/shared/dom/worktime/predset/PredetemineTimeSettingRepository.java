/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.Optional;

/**
 * The Interface PredetemineTimeSetRepository.
 */
public interface PredetemineTimeSettingRepository {
	
	/**
	 * Find by work time code.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 * @return the predetemine time set
	 */
	public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode);
	
	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void save(PredetemineTimeSetting domain);

	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 */
	public void remove(String companyId,String workTimeCode);
}
