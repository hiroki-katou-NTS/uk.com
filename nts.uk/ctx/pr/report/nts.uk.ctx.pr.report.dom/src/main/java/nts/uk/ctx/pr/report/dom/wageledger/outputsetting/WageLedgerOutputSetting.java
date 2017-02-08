/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Class OutputSetting.
 */
@Getter
public class WageLedgerOutputSetting {
	
	/** The code. */
	private WLOutputSettingCode code;
	
	/** The name. */
	private WLOutputSettingName name;
	
	/** The once sheet per person. */
	private boolean onceSheetPerPerson;
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The category settings. */
	private List<WageLedgerCategorySetting> categorySettings;

	/**
	 * Instantiates a new wage ledger output setting.
	 *
	 * @param code the code
	 * @param name the name
	 * @param onceSheetPerPerson the once sheet per person
	 * @param companyCode the company code
	 * @param categorySettings the category settings
	 */
	public WageLedgerOutputSetting(WLOutputSettingCode code, WLOutputSettingName name, boolean onceSheetPerPerson,
			CompanyCode companyCode, List<WageLedgerCategorySetting> categorySettings) {
		super();
		this.code = code;
		this.name = name;
		this.onceSheetPerPerson = onceSheetPerPerson;
		this.companyCode = companyCode;
		this.categorySettings = categorySettings;
	}
	
	
}
