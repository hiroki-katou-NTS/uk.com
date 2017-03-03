/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Interface WageTableHeadGetMemento.
 */
public interface WageTableHeadGetMemento {

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
	WageTableCode getCode();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	WageTableName getName();

	/**
	 * Gets the demension set.
	 *
	 * @return the demension set
	 */
	DemensionalMode getDemensionSetting();

	/**
	 * Gets the memo.
	 *
	 * @return the memo
	 */
	Memo getMemo();

}
