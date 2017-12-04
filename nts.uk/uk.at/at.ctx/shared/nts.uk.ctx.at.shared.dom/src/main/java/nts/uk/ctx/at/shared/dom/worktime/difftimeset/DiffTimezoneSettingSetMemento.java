/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;

/**
 * The Interface DiffTimezoneSettingSetMemento.
 */
public interface DiffTimezoneSettingSetMemento {

	/**
	 * Sets the employment timezone.
	 *
	 * @param employmentTimezone the new employment timezone
	 */
	public void setEmploymentTimezone(List<EmTimeZoneSet> employmentTimezone);

	/**
	 * Sets the OT timezone.
	 *
	 * @param OTTimezone the new OT timezone
	 */
	public void setOTTimezone(List<DiffTimeOTTimezoneSet> OTTimezone);

}
