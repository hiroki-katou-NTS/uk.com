/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;

import java.util.List;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Interface WLOutputSettingGetMemento.
 */
public interface WLOutputSettingGetMemento {
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	WLOutputSettingCode getCode();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	WLOutputSettingName getName();
	
	/**
	 * Gets the once sheet per person.
	 *
	 * @return the once sheet per person
	 */
	Boolean getOnceSheetPerPerson();
	
	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();
	
	/**
	 * Gets the category settings.
	 *
	 * @return the category settings
	 */
	List<WLCategorySetting> getCategorySettings();

}
