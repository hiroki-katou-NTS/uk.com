/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;

/**
 * The Interface DiffTimezoneSettingGetMemento.
 */
public interface DiffTimezoneSettingGetMemento {

	/**
	 * Gets the employment timezones.
	 *
	 * @return the employment timezones
	 */
	public List<EmTimeZoneSet> getEmploymentTimezones();

	/**
	 * Gets the OT timezones.
	 *
	 * @return the OT timezones
	 */
	public List<DiffTimeOTTimezoneSet> getOTTimezones();

}
