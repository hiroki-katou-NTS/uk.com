/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.autocalsetting.use;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.UseClassification;

/**
 * The Interface UseUnitAutoCalSettingSetMemento.
 */
public interface UseUnitAutoCalSettingSetMemento {
	
	void  setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the use job set.
	 *
	 * @param useJobSet the new use job set
	 */
	void  setUseJobSet(UseClassification useJobSet);

	/**
	 * Sets the use wkp set.
	 *
	 * @param useWkpSet the new use wkp set
	 */
	void  setUseWkpSet(UseClassification useWkpSet);
	
	/**
	 * Sets the use jobwkp set.
	 *
	 * @param useJobwkpSet the new use jobwkp set
	 */
	void  setUseJobwkpSet(UseClassification useJobwkpSet);
}
