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
	 * Find.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the worktime code
	 * @return the optional
	 */
	public Optional<FlexWorkSetting> find(String companyId,String workTimeCode);

	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	public void add(FlexWorkSetting domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	public void update(FlexWorkSetting domain);
	
	/**
	 * Removes the.
	 *
	 * @param companyId the company id
	 * @param workTimeCode the work time code
	 */
	public void remove(String companyId,String workTimeCode);

}
