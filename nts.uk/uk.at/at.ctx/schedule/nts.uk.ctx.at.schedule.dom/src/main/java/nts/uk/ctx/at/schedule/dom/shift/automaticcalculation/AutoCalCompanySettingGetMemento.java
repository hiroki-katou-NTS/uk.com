/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.automaticcalculation;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface AutoCalCompanySettingGetMemento.
 */
public interface AutoCalCompanySettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	
	/**
	 * Gets the auto cal overtime setting.
	 *
	 * @return the auto cal overtime setting
	 */
	AutoCalOvertimeSetting getAutoCalOvertimeSetting();
	
	/**
	 * Gets the auto cal flex overtime setting.
	 *
	 * @return the auto cal flex overtime setting
	 */
	AutoCalFlexOvertimeSetting getAutoCalFlexOvertimeSetting();
	
	/**
	 * Gets the auto cal rest time setting.
	 *
	 * @return the auto cal rest time setting
	 */
	AutoCalRestTimeSetting getAutoCalRestTimeSetting();
	
}
