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
}
