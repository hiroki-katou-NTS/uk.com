/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingform;

/**
 * The Interface SettingFlexWorkRepository.
 */
public interface SettingFlexWorkRepository {

	/**
	 * Update.
	 *
	 * @param setting the setting
	 */
	void update(SettingFlexWork setting);

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @return the setting flex work
	 */
	SettingFlexWork find(String companyId);
}
