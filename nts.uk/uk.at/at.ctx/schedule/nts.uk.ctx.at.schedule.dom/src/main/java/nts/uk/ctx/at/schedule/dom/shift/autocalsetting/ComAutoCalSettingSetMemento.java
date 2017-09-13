/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface AutoCalCompanySettingSetMemento.
 */
public interface ComAutoCalSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param setCompanyId the new company id
	 */
	void  setCompanyId(CompanyId setCompanyId);
	
	/**
	 * Sets the auto cal overtime setting.
	 *
	 * @param autoCalOvertimeSetting the new auto cal overtime setting
	 */
	void  setAutoCalOvertimeSetting(AutoCalOvertimeSetting autoCalOvertimeSetting);	
	
	/**
	 * Sets the auto cal flex overtime setting.
	 *
	 * @param autoCalFlexOvertimeSetting the new auto cal flex overtime setting
	 */
	void  setAutoCalFlexOvertimeSetting(AutoCalFlexOvertimeSetting autoCalFlexOvertimeSetting);	
	
	/**
	 * Sets the auto cal rest time setting.
	 *
	 * @param autoCalFlexOvertimeSetting the new auto cal rest time setting
	 */
	void  setAutoCalRestTimeSetting(AutoCalRestTimeSetting autoCalFlexOvertimeSetting);	
}
