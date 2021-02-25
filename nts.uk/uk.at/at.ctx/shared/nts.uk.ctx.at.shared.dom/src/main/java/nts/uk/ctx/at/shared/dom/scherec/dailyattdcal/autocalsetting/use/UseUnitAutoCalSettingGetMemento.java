/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;

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
	ApplyAtr getUseJobSet();

	/**
	 * Gets the use wkp set.
	 *
	 * @return the use wkp set
	 */
	ApplyAtr getUseWkpSet();

	/**
	 * Gets the use jobwkp set.
	 *
	 * @return the use jobwkp set
	 */
	ApplyAtr getUseJobwkpSet();
}
