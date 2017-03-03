/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.mode.DemensionalMode;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Interface WageTableHeadSetMemento.
 */
public interface WageTableHeadSetMemento {

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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(WageTableName name);

	/**
	 * Sets the demension set.
	 *
	 * @param demensionSet the new demension set
	 */
	void setDemensionSetting(DemensionalMode demensionSetting);

	/**
	 * Sets the memo.
	 *
	 * @param memo the new memo
	 */
	void setMemo(Memo memo);

}
