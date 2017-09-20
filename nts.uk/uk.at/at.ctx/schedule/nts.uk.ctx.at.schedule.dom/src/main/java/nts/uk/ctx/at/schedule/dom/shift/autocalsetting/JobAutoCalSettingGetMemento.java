/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface JobAutoCalSettingGetMemento.
 */
public interface JobAutoCalSettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the position id.
	 *
	 * @return the position id
	 */
	PositionId getPositionId();
	
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
