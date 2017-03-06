/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;

import java.util.List;

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
	void remove(WLOutputSettingCode code, CompanyCode companyCode);
	
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
	boolean isExist(WLOutputSettingCode code, CompanyCode companyCode);
	
	/**
	 * Find all output setting in company.
	 *
	 * @param companyCode the company code
	 * @param isLoadHeaderDataOnly the is load header data only
	 * @return the list
	 */
	List<WLOutputSetting> findAll(CompanyCode companyCode, boolean isLoadHeaderDataOnly);
}
