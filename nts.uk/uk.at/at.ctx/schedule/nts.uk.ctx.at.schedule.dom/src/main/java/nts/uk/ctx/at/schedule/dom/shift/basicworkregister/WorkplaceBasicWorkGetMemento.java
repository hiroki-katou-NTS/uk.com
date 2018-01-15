/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import java.util.List;

/**
 * The Interface WorkplaceBasicWorkGetMemento.
 */
public interface WorkplaceBasicWorkGetMemento {
	
	/**
	 * Gets the work place id.
	 *
	 * @return the work place id
	 */
	String getWorkPlaceId();
	
	/**
	 * Gets the basic work setting.
	 *
	 * @return the basic work setting
	 */
	List<BasicWorkSetting> getBasicWorkSetting();
}
