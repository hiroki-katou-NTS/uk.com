/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.List;

/**
 * The Interface CompanyBasicWorkSetMemento.
 */
public interface CompanyBasicWorkSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	/**
	 * Sets the basic work setting.
	 *
	 * @param basicWorkSetting the new basic work setting
	 */
	void setBasicWorkSetting(List<BasicWorkSetting> basicWorkSetting);
}
