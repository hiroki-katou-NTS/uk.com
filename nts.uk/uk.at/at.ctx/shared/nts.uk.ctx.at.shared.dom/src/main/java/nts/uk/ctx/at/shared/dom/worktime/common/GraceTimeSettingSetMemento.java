/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface GraceTimeSettingSetMemento.
 */
public interface GraceTimeSettingSetMemento {
	
	/**
	 * Sets the include working hour.
	 *
	 * @param includeWorkingHour the new include working hour
	 */
	void setIncludeWorkingHour(boolean includeWorkingHour);
	
	
	/**
	 * Sets the grace time.
	 *
	 * @param graceTime the new grace time
	 */
	void setGraceTime(LateEarlyGraceTime graceTime);
}
