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
	 * Gets the employment timezone.
	 *
	 * @return the employment timezone
	 */
	public List<EmTimeZoneSet> getEmploymentTimezone();

	/**
	 * Gets the OT timezone.
	 *
	 * @return the OT timezone
	 */
	public List<DiffTimeOTTimezoneSet> getOTTimezone();

}
