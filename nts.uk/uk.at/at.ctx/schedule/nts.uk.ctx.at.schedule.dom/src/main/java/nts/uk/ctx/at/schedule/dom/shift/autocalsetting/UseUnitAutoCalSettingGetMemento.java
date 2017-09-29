/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface UseUnitAutoCalSettingGetMemento.
 */
public interface UseUnitAutoCalSettingGetMemento {
	
	/**
	 * Gets the use job set.
	 *
	 * @return the use job set
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the use job set.
	 *
	 * @return the use job set
	 */
	UseClassification getUseJobSet();
	
	/**
	 * Gets the use wkp set.
	 *
	 * @return the use wkp set
	 */
	UseClassification getUseWkpSet();
	
	/**
	 * Gets the use jobwkp set.
	 *
	 * @return the use jobwkp set
	 */
	UseClassification getUseJobwkpSet();
}
