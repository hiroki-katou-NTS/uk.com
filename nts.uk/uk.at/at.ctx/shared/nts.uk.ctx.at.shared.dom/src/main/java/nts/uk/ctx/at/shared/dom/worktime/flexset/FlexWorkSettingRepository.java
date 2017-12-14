/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.Optional;

/**
 * The Interface FlexWorkSettingRepository.
 */
public interface FlexWorkSettingRepository {
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 * @return the optional
	 */
	public Optional<FlexWorkSetting> findById(String companyId,String worktimeCode);
	
	
	/**
	 * Save flex work setting.
	 *
	 * @param domain the domain
	 */
	public void saveFlexWorkSetting(FlexWorkSetting domain);

}
