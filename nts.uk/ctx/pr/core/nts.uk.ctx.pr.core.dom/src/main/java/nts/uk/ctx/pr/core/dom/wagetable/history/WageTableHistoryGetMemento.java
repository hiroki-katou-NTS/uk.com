/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.element.WageTableElement;

/**
 * The Interface WageTableHistoryGetMemento.
 */
public interface WageTableHistoryGetMemento {

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	WageTableCode getWageTableCode();

	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	String getHistoryId();

	/**
	 * Gets the apply range.
	 *
	 * @return the apply range
	 */
	MonthRange getApplyRange();

	/**
	 * Gets the demension items.
	 *
	 * @return the demension items
	 */
	List<WageTableElement> getDemensionDetail();

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	List<WageTableItem> getValueItems();

}
