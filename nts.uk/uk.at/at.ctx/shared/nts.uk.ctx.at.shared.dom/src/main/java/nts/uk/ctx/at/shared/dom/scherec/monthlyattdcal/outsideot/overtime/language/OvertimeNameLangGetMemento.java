/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;

/**
 * The Interface OvertimeNameLangGetMemento.
 */
public interface OvertimeNameLangGetMemento {
	
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
	public OvertimeName getName();
	
	
	/**
	 * Gets the language id.
	 *
	 * @return the language id
	 */
	public LanguageId getLanguageId();
	
	
	/**
	 * Gets the overtime no.
	 *
	 * @return the overtime no
	 */
	public OvertimeNo getOvertimeNo();
}
