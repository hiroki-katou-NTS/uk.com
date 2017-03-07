/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;

/**
 * The Interface WageTableHistorySetMemento.
 */
public interface WageTableHistorySetMemento {

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	void setCompanyCode(CompanyCode companyCode);

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	void setCode(WageTableCode code);

	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	void  setHistoryId(String historyId);

	/**
	 * Sets the apply range.
	 *
	 * @param applyRange the new apply range
	 */
	void setApplyRange(MonthRange applyRange);

	/**
	 * Gets the demension items.
	 *
	 * @return the demension items
	 */
	void setDemensionDetail(List<WageTableDemensionDetail> demensionDetails);

	/**
	 * Sets the elements.
	 *
	 * @param elements the new elements
	 */
	void setValueItems(List<WageTableItem> elements);

}
