/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.List;

/**
 * The Interface WorkplaceBasicWorkSetMemento.
 */
public interface WorkplaceBasicWorkSetMemento {

	/**
	 * Sets the work place id.
	 *
	 * @param workplaceId the new work place id
	 */
	void setWorkPlaceId(String workplaceId);
	
	/**
	 * Sets the basic work setting.
	 *
	 * @param basicWorkSetting the new basic work setting
	 */
	void setBasicWorkSetting(List<BasicWorkSetting> basicWorkSetting);
}
