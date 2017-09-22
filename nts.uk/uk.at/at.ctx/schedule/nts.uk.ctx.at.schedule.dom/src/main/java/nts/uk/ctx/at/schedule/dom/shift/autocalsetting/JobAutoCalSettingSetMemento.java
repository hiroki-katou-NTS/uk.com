/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface JobAutoCalSettingSetMemento.
 */
public interface JobAutoCalSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void  setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the position id.
	 *
	 * @param positionId the new position id
	 */
	void  setPositionId(PositionId positionId);
	
	/**
	 * Sets the auto cal overtime setting.
	 *
	 * @param autoCalOvertimeSetting the new auto cal overtime setting
	 */
	void  setNormalOTTime(AutoCalOvertimeSetting normalOTTime);
	
	/**
	 * Sets the auto cal flex overtime setting.
	 *
	 * @param autoCalFlexOvertimeSetting the new auto cal flex overtime setting
	 */
	void  setFlexOTTime(AutoCalFlexOvertimeSetting flexOTTime);
	
	/**
	 * Sets the auto cal rest time setting.
	 *
	 * @param autoCalRestTimeSetting the new auto cal rest time setting
	 */
	void  setRestTime(AutoCalRestTimeSetting restTime);
	
}
