/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.LanguageId;

/**
 * The Interface OutsideOTBRDItemLangGetMemento.
 */
public interface OutsideOTBRDItemLangGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();
	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public BreakdownItemName getName();
	
	
	/**
	 * Gets the language id.
	 *
	 * @return the language id
	 */
	public LanguageId getLanguageId();
	
	
	/**
	 * Gets the breakdown item no.
	 *
	 * @return the breakdown item no
	 */
	public BreakdownItemNo getBreakdownItemNo();

}
