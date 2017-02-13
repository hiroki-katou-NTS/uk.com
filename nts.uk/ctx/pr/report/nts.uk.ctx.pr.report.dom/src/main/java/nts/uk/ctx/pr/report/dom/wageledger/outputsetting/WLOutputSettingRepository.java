/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Interface WLOutputSettingRepository.
 */
public interface WLOutputSettingRepository {
	
	
	/**
	 * Creates the.
	 *
	 * @param outputSetting the output setting
	 */
	void create(WLOutputSetting outputSetting);
	
	/**
	 * Update.
	 *
	 * @param outputSetting the output setting
	 */
	void update(WLOutputSetting outputSetting);
	
	/**
	 * Removes the.
	 *
	 * @param outputSetting the output setting
	 */
	void remove(WLOutputSettingCode code);
	
	/**
	 * Find.
	 *
	 * @param code the code
	 * @param companyCode the company code
	 * @return the WL output setting
	 */
	WLOutputSetting findByCode(WLOutputSettingCode code, CompanyCode companyCode);
	
	/**
	 * Checks if is exist.
	 *
	 * @param code the code
	 * @return true, if is exist
	 */
	boolean isExist(WLOutputSettingCode code);
}
