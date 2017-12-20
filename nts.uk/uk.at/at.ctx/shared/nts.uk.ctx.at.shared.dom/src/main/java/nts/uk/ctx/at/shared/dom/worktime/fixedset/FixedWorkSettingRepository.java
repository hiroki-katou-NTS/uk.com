/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.Optional;

/**
 * The Interface FixedWorkSettingRepository.
 */
public interface FixedWorkSettingRepository {

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 * @return the optional
	 */
	Optional<FixedWorkSetting> find(String companyId, String workTimeCode);

	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void save(FixedWorkSetting domain);
}
