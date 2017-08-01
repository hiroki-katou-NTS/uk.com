/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.List;

/**
 * The Interface CompanyBasicWorkGetMemento.
 */
public interface CompanyBasicWorkGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the basic work setting.
	 *
	 * @return the basic work setting
	 */
	List<BasicWorkSetting> getBasicWorkSetting();
}
