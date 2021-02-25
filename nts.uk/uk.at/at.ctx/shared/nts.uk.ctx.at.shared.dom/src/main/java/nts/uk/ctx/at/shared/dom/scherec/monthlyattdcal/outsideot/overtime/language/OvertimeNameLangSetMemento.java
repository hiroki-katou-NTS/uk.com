/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;

/**
 * The Interface OvertimeNameLangSetMemento.
 */
public interface OvertimeNameLangSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(CompanyId companyId);
	
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(OvertimeName name);
	
	
	/**
	 * Sets the language id.
	 *
	 * @param languageId the new language id
	 */
	public void setLanguageId(LanguageId languageId);
	
	
	/**
	 * Sets the overtime no.
	 *
	 * @param overtimeNo the new overtime no
	 */
	public void setOvertimeNo(OvertimeNo overtimeNo);

}
