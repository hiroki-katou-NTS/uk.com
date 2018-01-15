/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface GraceTimeSettingGetMemento.
 */
public interface GraceTimeSettingGetMemento {
	
	/**
	 * Gets the include working hour.
	 *
	 * @return the include working hour
	 */
	boolean getIncludeWorkingHour();
	
	
	/**
	 * Gets the grace time.
	 *
	 * @return the grace time
	 */
	LateEarlyGraceTime getGraceTime();

}
