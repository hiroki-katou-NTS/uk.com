/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

/**
 * The Interface FlexWorkSettingRepository.
 */
public interface FlexWorkSettingRepository {
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<FlexWorkSetting> findAll(String companyId);
	
	
	/**
	 * Save flex work setting.
	 *
	 * @param domain the domain
	 */
	public void saveFlexWorkSetting(FlexWorkSetting domain);

}
