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
	 * Sets the employment timezones.
	 *
	 * @param employmentTimezones the new employment timezones
	 */
	public void setEmploymentTimezones(List<EmTimeZoneSet> employmentTimezones);

	/**
	 * Sets the OT timezones.
	 *
	 * @param oTTimezones the new OT timezones
	 */
	public void setOTTimezones(List<DiffTimeOTTimezoneSet> oTTimezones);

}
