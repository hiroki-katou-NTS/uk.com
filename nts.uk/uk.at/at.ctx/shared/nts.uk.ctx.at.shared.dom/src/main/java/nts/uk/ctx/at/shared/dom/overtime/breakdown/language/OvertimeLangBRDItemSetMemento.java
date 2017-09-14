/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.breakdown.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.overtime.language.LanguageId;

/**
 * The Interface OvertimeLangBRDItemSetMemento.
 */
public interface OvertimeLangBRDItemSetMemento {
	
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
	public void setName(BreakdownItemName name);
	
	
	/**
	 * Sets the language id.
	 *
	 * @param languageId the new language id
	 */
	public void setLanguageId(LanguageId languageId);
	
	
	/**
	 * Sets the breakdown item no.
	 *
	 * @param breakdownItemNo the new breakdown item no
	 */
	public void setBreakdownItemNo(BreakdownItemNo breakdownItemNo);

}
