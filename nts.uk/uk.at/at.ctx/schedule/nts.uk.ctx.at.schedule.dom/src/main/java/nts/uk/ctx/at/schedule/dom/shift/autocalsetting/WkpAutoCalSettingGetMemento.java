/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Interface WkpAutoCalSettingGetMemento.
 */
public interface WkpAutoCalSettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	

	/**
	 * Gets the wkp id.
	 *
	 * @return the wkp id
	 */
	WorkplaceId getWkpId();
	

	/**
	 * Gets the normal OT time.
	 *
	 * @return the normal OT time
	 */
	AutoCalOvertimeSetting getNormalOTTime();
	

	/**
	 * Gets the flex OT time.
	 *
	 * @return the flex OT time
	 */
	AutoCalFlexOvertimeSetting getFlexOTTime();
	

	/**
	 * Gets the rest time.
	 *
	 * @return the rest time
	 */
	AutoCalRestTimeSetting getRestTime();
	
	
}
