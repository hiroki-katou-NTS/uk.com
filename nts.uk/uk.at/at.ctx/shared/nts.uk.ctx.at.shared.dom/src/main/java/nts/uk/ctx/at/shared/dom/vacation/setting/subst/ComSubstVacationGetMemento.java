/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface ComSubstVacationGetMemento.
 */
public interface ComSubstVacationGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the setting.
	 *
	 * @return the setting
	 */
	SubstVacationSetting getSetting();
	//管理区分 
	ManageDistinct getManageDistinct();
	
	//紐付け管理区分
	ManageDistinct getLinkingManagementATR();
}
