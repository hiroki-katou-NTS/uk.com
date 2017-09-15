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
 * The Interface OvertimeLangBRDItemGetMemento.
 */
public interface OvertimeLangBRDItemGetMemento {
	
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
