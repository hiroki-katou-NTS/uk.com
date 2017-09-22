/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * The Interface WkpJobAutoCalSettingSetMemento.
 */
public interface WkpJobAutoCalSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void  setCompanyId(CompanyId companyId);

	

	/**
	 * Sets the wkp id.
	 *
	 * @param workplaceId the new wkp id
	 */
	void  setWkpId(WorkplaceId workplaceId);

	/**
	 * Sets the position id.
	 *
	 * @param positionId the new position id
	 */
	void  setPositionId(PositionId positionId);

	/**
	 * Sets the normal OT time.
	 *
	 * @param normalOTTime the new normal OT time
	 */
	void  setNormalOTTime(AutoCalOvertimeSetting normalOTTime);

	

	/**
	 * Sets the flex OT time.
	 *
	 * @param flexOTTime the new flex OT time
	 */
	void  setFlexOTTime(AutoCalFlexOvertimeSetting flexOTTime);

	

	/**
	 * Sets the rest time.
	 *
	 * @param restTime the new rest time
	 */
	void  setRestTime(AutoCalRestTimeSetting restTime);
}
