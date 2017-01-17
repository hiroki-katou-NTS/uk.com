/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Interface OutputSettingRepository.
 */
public interface OutputSettingRepository {
	
	/**
	 * Save.
	 *
	 * @param outputSetting the output setting
	 */
	void save(WageLedgerOutputSetting outputSetting);
	
	/**
	 * Removes the.
	 *
	 * @param outputSetting the output setting
	 */
	void remove(WageLedgerOutputSetting outputSetting);
	
	/**
	 * Find.
	 *
	 * @param code the code
	 * @param companyCode the company code
	 * @return the wage ledger output setting
	 */
	WageLedgerOutputSetting find(OutputSettingCode code, CompanyCode companyCode);
}
